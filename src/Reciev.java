/**
 * Created by sliver_note on 16.12.2015.
 */

import java.lang.invoke.SerializedLambda;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;



public class Reciev {


    private static SerialPort serialPort;
    private static int count;
    private static int countNum;



    public static void main(String args[]) {
        try {
            int port = 20083;


            serialPort = new SerialPort("COM4");
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
            DatagramSocket dsocket = new DatagramSocket(port);
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while(true){
            dsocket.receive(packet);
            String msg = new String(buffer, 0, packet.getLength());
//                System.out.println(packet.getAddress().getHostName() + ": " + buffer.length + " === "
//                        + msg);
            int[] numArr = Arrays.stream(msg.split(" ")).mapToInt(Integer::parseInt).toArray();
            System.out.print(numArr.length + " :");
            for (int i = 0; i < numArr.length; i++) {
                System.out.print(numArr[i] + ", ");
            }

                System.out.println(buffer.length);
                System.out.println();
            packet.setLength(buffer.length);
                    serialPort.writeString("s");
                    serialPort.writeString(Integer.toString(numArr.length));
                    serialPort.writeString(":");
                    for (int i = 0; i < numArr.length/2; i++) {
                        serialPort.writeString(Integer.toString(numArr[i]));
                        serialPort.writeString("/");
                }
                serialPort.writeString("d");
                for (int i = numArr.length/2 + 1; i < numArr.length; i++) {
                    serialPort.writeString(Integer.toString(numArr[i]));
                    serialPort.writeString("/");
                }

        }
        } catch (Exception e) {
            System.err.println(e);
        }
        finally {

        }
    }


    private static class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
                try {
                    String data = serialPort.readString(event.getEventValue());
                    System.out.println(data);
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }
    public static int timer() throws InterruptedException {
        Thread.sleep(10);
        count++;
        return count;
    }
}
