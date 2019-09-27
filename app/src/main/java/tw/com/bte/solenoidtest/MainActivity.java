package tw.com.bte.solenoidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private ReadThread mReadThread;
    private Button b1, b2, b3, b4, b5, b6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.btn1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn6);
        b6.setOnClickListener(this);

        Uart_Init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        int action = v.getId();
        byte[] data = new byte[]{(byte) 0xA6, 7, 6, 0, 0, 0, 0, 0, 0};
        switch (action){
            case R.id.btn1:
                data[3] = 1;
                SendUart(data);
                break;
            case R.id.btn2:
                data[4] = 1;
                SendUart(data);
                break;
            case R.id.btn3:
                data[5] = 1;
                SendUart(data);
                break;
            case R.id.btn4:
                data[6] = 1;
                SendUart(data);
                break;
            case R.id.btn5:
                data[7] = 1;
                SendUart(data);
                break;
            case R.id.btn6:
                data[8] = 1;
                SendUart(data);
                break;
        }
    }

    private void SendUart(byte[] data){
        try{
            mOutputStream.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Uart_Init(){
        try {
            SerialPort serialPort = new SerialPort(new File("/dev/ttyS3"), 115200, 0);
            mInputStream = serialPort.getInputStream();
            mOutputStream = serialPort.getOutputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class ReadThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(!isInterrupted()){
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        Log.d("UART_R", String.format("%02X %02X %02X %02X %02X %02X %02X %02X %02X", buffer[0], buffer[1], buffer[2], buffer[3], buffer[4], buffer[5], buffer[6], buffer[7], buffer[8]));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
