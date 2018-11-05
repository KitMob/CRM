package net.brigs.crm.HttpClient.client;

public class GetMessageError {
    public String getMessageError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            if (message.toString().equals(cause.toString())) {

            } else {
                message += " " + cause.getMessage();
            }
        }
        return message;
    }
}
