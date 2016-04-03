/**
 * Created by sliver_note on 16.12.2015.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    private static String msg;
    private static int count;
    private static int countNum;



    public static void main(String args[]) {
        try {
            int port = 20083;



            serialPort = new SerialPort("COM4");
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_57600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
            DatagramSocket dsocket = new DatagramSocket(port);
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            serialPort.writeString(String.valueOf(reader.readLine()));
            while(true){
            dsocket.receive(packet);
              msg = new String(buffer, 0, packet.getLength());
//                System.out.println(packet.getAddress().getHostName() + ": " + buffer.length + " === "
//                        + msg);

            int[] numArr = Arrays.stream(msg.split(" ")).mapToInt(Integer::parseInt).toArray();
//                transmit(numArr);
                System.out.println(numArr.length + " " + msg + '/');
                serialPort.writeString(numArr.length + msg + '/');
//                reciev();
            packet.setLength(buffer.length);


        }
        } catch (Exception e) {
            System.err.println(e);
        }
        finally {

        }
    }

//    public static void transmit(int[] numArr){
////        System.out.println("<Start>");
////        System.out.print("<" + numArr.length + ">");
////        for (int i = 0; i < numArr.length; i++) {
////            System.out.print(numArr[i] + ", ");
////        }
//        System.out.println();
//        try {
////            while(!serialPort.readString().equals("Ready!"));
//            serialPort.writeString(msg);
////            serialPort.writeString("<" + Integer.toString(numArr.length) + ">");
////            for (int i = 0; i < numArr.length; i++) {
////                serialPort.writeString(Integer.toString(numArr[i]));
////                serialPort.writeString("/");
////            }
////            serialPort.writeString("<Stop>");
//        } catch (SerialPortException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public static void reciev(){
//        try {
//            while (!serialPort.readString().equals("Ready"));
//        } catch (SerialPortException e) {
//            e.printStackTrace();
//        }
//    }



    private static class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
                try {
                    String data = serialPort.readString(event.getEventValue());
                    System.out.println();
                    System.out.println(data);
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }
}
