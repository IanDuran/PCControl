import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RemoteServer {
    private final int SOCKET_NUMBER = 6666;
    private Robot robot;
    private ServerSocket serverSocket;
    private boolean isShiftPressed;
    private boolean isControlPressed;
    private boolean isAltPressed;
    //Test comment
    public RemoteServer(){
        try {
            robot = new Robot();
            serverSocket = new ServerSocket(SOCKET_NUMBER);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void typeInput(){
        while(true){
            try{
                Socket clientSocket = serverSocket.accept();
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                this.typeKey(input.readInt());
                clientSocket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void typeKey(int keyCode){
        robot.keyPress(keyCode);
        if(KeyEvent.VK_SHIFT == keyCode){
            if(isShiftPressed) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
                isShiftPressed = false;
            } else
                isShiftPressed = true;

        }else if(KeyEvent.VK_CONTROL == keyCode){
            if(isControlPressed) {
                robot.keyRelease(KeyEvent.VK_CONTROL);
                isControlPressed = false;
            } else
                isControlPressed = true;

        }else if(KeyEvent.VK_ALT == keyCode){
            if(isAltPressed) {
                robot.keyRelease(KeyEvent.VK_ALT);
                isAltPressed = false;
            } else
                isAltPressed = true;

        }else{
            robot.keyRelease(keyCode);

            if(isShiftPressed) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
                isShiftPressed = false;
            }
            if(isControlPressed) {
                robot.keyRelease(KeyEvent.VK_CONTROL);
                isControlPressed = false;
            }
            if(isAltPressed) {
                robot.keyRelease(KeyEvent.VK_ALT);
                isAltPressed = false;
            }
        }
    }

    public static void main(String... args){
        RemoteServer rs = new RemoteServer();
        rs.typeInput();
    }
}
