#SolenoidTest
This is for test solenoid id via RS232

Following step to connect UART
1. UART init and setup UART output
example: 
SerialPort serialPort = new SerialPort(new File("/dev/ttyS3"), 115200, 0);
OutputStream outputStream = serialPort.getOutputStream();
2. Send message to device
example:
outputStream.Write(data)
