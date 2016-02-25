/**
 * Created by sliver_note on 17.12.2015.
 */
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Test {
    private static SerialPort serialPort;

    public static void main(String[] args) {
        //������� � ����������� ��� �����
        serialPort = new SerialPort("COM4");
        try {
            //��������� ����
            serialPort.openPort();
            //���������� ���������
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            //�������� ���������� ���������� �������
            //������������� ����� ������� � �����
            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
            //���������� ������ ����������
            serialPort.writeString("Get data");
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    private static class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
                try {
                    //�������� ����� �� ����������, ������������ ������ � �.�.
                    String data = serialPort.readString(event.getEventValue());
                    System.out.println(data);
                    //� ����� ���������� ������
                    serialPort.writeString("Get data");
                }
                catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }
}
