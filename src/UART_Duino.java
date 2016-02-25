/**
 * Created by sliver_note on 17.12.2015.
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;

public class UART_Duino {
    public static void main(String[] args) {
        SerialPort serialPort = new SerialPort("COM4");
        try {
            System.out.println("Port opened: " + serialPort.openPort());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(UART_Duino.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Params setted: " + serialPort.setParams(9600, 8, 1, 0));
            System.out.println("\"Hello World!!!\" successfully writen to port: " + serialPort.writeBytes("J".getBytes()));

                byte[] s = serialPort.readBytes();
                System.out.println("Recieve data: " + s);

            System.out.println("Port closed: " + serialPort.closePort());
        }
        catch (SerialPortException ex){
            System.out.println(ex);
        }
    }
}
