package persistance;

import models.Movie;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * класс для записи коллекции в файл
 */
public class CsvEncoder {
    /**
     * файл куда писать
     */
    private final File out;
    /**
     * разделитель для csv
     */
    private final String SEP = ";";

    public CsvEncoder(File out) {
        this.out = out;
    }

    /**
     * запись коллекции в файл
     * @param objects объекты которые нужно сохранить
     * @throws IOException
     * @throws IllegalAccessException
     */
    public void encode(Object[] objects) throws IOException, IllegalAccessException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(out));
        try {
            writer.write(genHeader(objects[0].getClass(), "") + "\n");
            for (Object obj : objects) {
                String res = encodeObj(obj);
                writer.write(res + "\n");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("коллекция пустая");
        }
        writer.close();
    }

    /**
     * генирирует заголовок для csv файла(вложенные поля называются Class.fieldName)
     * @param cls
     * @param prefix
     * @return
     */
    private String genHeader(Class<?> cls, String prefix) {
        StringBuilder stringBuilder = new StringBuilder();

        for(Field field: cls.getDeclaredFields()) {
            if (checkIfSerializable(field.getType())) {
                stringBuilder.append(genHeader(field.getType(), field.getType().getSimpleName()));
            } else {
                stringBuilder.append(prefix).append(prefix.equals("") ? "" : ".").append(field.getName()).append(SEP);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * рекурсивная кодировка объекта в csv
     * @param obj объект для кодирования
     * @return закодированная строка
     * @throws IllegalAccessException
     */
    private String encodeObj(Object obj) throws IllegalAccessException {
        Class<?> cls = obj.getClass();

        StringBuilder stringBuilder = new StringBuilder();
        for(Field field: cls.getDeclaredFields()) {
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (checkIfSerializable(field.getType())) {
                stringBuilder.append(encodeObj(field.get(obj)));
            } else {
                stringBuilder.append(field.get(obj)).append(SEP);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * проверка, является ли поле объектом для сериализации(Есть ли аннотация @CsvSerializable)
     * @param cls
     * @return
     */
    private boolean checkIfSerializable(Class<?> cls) {
        return CsvSerilizable.class.isAssignableFrom(cls);
    }
}
