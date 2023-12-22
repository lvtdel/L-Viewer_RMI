package BLL;

import GUI.ChatForm;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ClientChatBLL {
    ChatForm clientChatForm;

    public ClientChatBLL() {
        EventQueue.invokeLater(() -> {
            try {
                clientChatForm = new ChatForm();
            } catch (Exception e) {
                e.printStackTrace();
            }

            clientChatForm.micCheckBox.addItemListener(e -> {
                clientChatForm.changeMicState();
                this.onMicStateChange(clientChatForm.micCheckBox.isSelected());
            });

            clientChatForm.speakerCheckBox.addItemListener(e -> {
                clientChatForm.changeSpeakerState();
                this.onSpeakerStateChange(clientChatForm.speakerCheckBox.isSelected());
            });

            clientChatForm.btnSend.addActionListener(e -> {
                String message = clientChatForm.txtText.getText();
                onSendMessageClick(message);

                clientChatForm.addMessage("Me", message);
                clientChatForm.txtText.setText("");
            });
        });
    }

    private void onSpeakerStateChange(boolean state) {
        System.out.println("Mic state: " + state);
    }

    private void onMicStateChange(boolean state) {
        System.out.println("Speaker state: " + state);

    }

    private void onSendMessageClick(String message) {
        System.out.println("Send mess: " + message);
    }

    public void receiveMessage(String message) {
        clientChatForm.addMessage("Partner", message);
    }

    public static void main(String[] args) {
        ClientChatBLL clientChatBLL = new ClientChatBLL();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        clientChatBLL.receiveMessage("hello");

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        clientChatBLL.clientChatForm.setVisible(true);
    }
}
