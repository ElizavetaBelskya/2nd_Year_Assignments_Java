package ru.kpfu.itis.belskaya.listener;

import ru.kpfu.itis.belskaya.protocol.exceptions.MessageWorkException;
import ru.kpfu.itis.belskaya.protocol.messages.Message;

public class JoinListener extends AbstractServerEventListener {

    @Override
    public void handle(Message message) throws MessageWorkException {
        server.sendBroadCastMessage(message);
    }

}
