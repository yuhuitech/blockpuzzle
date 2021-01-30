package de.mwvb.blockpuzzle.messages;

/**
 * This represents a concrete displayable message.
 */
public class MessageObject {
    private final MessageFactory factory;
    private final int id;

    public MessageObject(MessageFactory factory, int id) {
        this.factory = factory;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void show() {
        factory.show(id);
    }
}
