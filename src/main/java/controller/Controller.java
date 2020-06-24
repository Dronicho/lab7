package controller;

import commands.Command;

/**
 * Интерфейс для контроллера
 */
public interface Controller {
    /**
     * вполняет команду, введенную пользователем
     *
     * @param command команда для исполнения
     */
    void execute(String command);

    /**
     * начинает слушать команды
     */
    void startListening();
}
