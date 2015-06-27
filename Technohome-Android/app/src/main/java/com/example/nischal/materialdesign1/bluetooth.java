package com.example.nischal.materialdesign1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;



public class bluetooth extends ActionBarActivity   {
    private android.support.v7.widget.Toolbar mytoolbar;
    protected static final int REQUEST_OK = 1;
    ImageButton switch1voice;
    Switch switch1;
    Switch switch2;
    Switch switch3;
    TextView textView, textView2, txtStringLength, sensorView3;
    Handler bluetoothIn;
    String[] state=new String[3];
    int[] voicestate=new int[3];


     String  mac="30:14:12:08:20:86";
    String voice1_on;
    String voice1_off;
    String voice2_on;
    String voice2_off;
    String voice3_off;
    String voice3_on;




    final int handlerState = 0;                        //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    RelativeLayout back;
    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    //static hataya hai (imp1)
    private   String address = mac;
   /* public void blink(){
        AlphaAnimation blinkanimation= new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        blinkanimation.setDuration(300); // duration - half a second
        blinkanimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        blinkanimation.setRepeatCount(3); // Repeat animation infinitely
        blinkanimation.setRepeatMode(Animation.REVERSE);
        bulb.startAnimation(blinkanimation);
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        mytoolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.app_bar);
        for(int i=0;i<3;i++)
        {
            voicestate[i]=10;
            state[i]="0";
        }
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        switch1voice = (ImageButton)findViewById(R.id.button1);
        textView=(TextView)findViewById(R.id.textView);
        textView2=(TextView)findViewById(R.id.textView2);
       SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
         String button1 = sharedPref.getString("example_text", "true");
        String button2 = sharedPref.getString("example_text2", "true");
        String  button3 = sharedPref.getString("example_text3","true");
         voice1_on=sharedPref.getString("voice1_on","true");
         voice1_off=sharedPref.getString("voice1_off","true");
        voice2_on=sharedPref.getString("voice2_on","true");
        voice2_off=sharedPref.getString("voice2_off","true");
         voice3_on=sharedPref.getString("voice3_on","true");
         voice3_off=sharedPref.getString("voice3_off","true");
         mac=sharedPref.getString("mac","true");
        Log.d("aman",voice1_on);





        //Link the buttons and textViews to respective views
        switch1 = (Switch) findViewById(R.id.switch1);
        switch2 = (Switch) findViewById(R.id.switch2);
        switch3 = (Switch) findViewById(R.id.switch3);
        //ledstate = (TextView) findViewById(R.id.textView);
        sensorView3 = (TextView) findViewById(R.id.sensorView3);
        sensorView3.setText(button1);
        textView.setText(button2);
        textView2.setText(button3);


        //bulb=(ImageView)findViewById(R.id.imageView);

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {                                     //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                      //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        //txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length();                          //get length of data received
                        // txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')                             //if it starts with # we know it is what we are looking for
                        {
                            String[] sensor3=new String[3];
                            sensor3[0] = recDataString.substring(1, 2);
                            sensor3[1] = recDataString.substring(3, 4);
                            sensor3[2] = recDataString.substring(5, 6);
                            state[0]=sensor3[0];
                            state[1]=sensor3[1];
                            state[2]=sensor3[2];



                            if(sensor3[0].equals("1")){
                                switch1.setChecked(true);
                                switch1.setText("ON");

                            }
                            if(sensor3[0].equals("0")){
                                switch1.setChecked(false);
                                switch1.setText("OFF");

                            }
                            if(sensor3[2].equals("1")){

                                switch3.setChecked(true);
                                switch3.setText("ON");

                            }
                            if(sensor3[2].equals("0")){
                                switch3.setChecked(false);
                                switch3.setText("OFF");

                            }
                            if(sensor3[1].equals("1")){

                                switch2.setChecked(true);
                                switch2.setText("ON");

                            }
                            if(sensor3[1].equals("0")){
                                switch2.setChecked(false);
                                switch2.setText("OFF");

                            }
                            if(voicestate[0]==1){
                                mConnectedThread.write("1");
                            }

                            if(voicestate[0]==0){
                                mConnectedThread.write("0");
                            }
                            if(voicestate[1]==1){
                                mConnectedThread.write("3");
                            }

                            if(voicestate[1]==0){
                                mConnectedThread.write("2");
                            }
                            if(voicestate[2]==1){
                                mConnectedThread.write("5");
                            }

                            if(voicestate[2]==0){
                                mConnectedThread.write("4");
                            }
                            for(int i=0;i<3;i++) {
                                voicestate[i] = 10;
                            }

                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };


        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED
        switch1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(state[0].equals("1")) {
                    mConnectedThread.write("0");    // Send "0" via Bluetooth
                    Toast.makeText(getBaseContext(), "Switching button 1 off", Toast.LENGTH_SHORT).show();
                }
                if(state[0].equals("0")){
                    mConnectedThread.write("1");    // Send "0" via Bluetooth
                    Toast.makeText(getBaseContext(), "Switching button 1 on", Toast.LENGTH_SHORT).show();
                }

            }
        });
        switch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state[1].equals("0")) {
                    mConnectedThread.write("3");    // Send "0" via Bluetooth
                    Toast.makeText(getBaseContext(), "Switching button 2 on", Toast.LENGTH_SHORT).show();
                }
                if(state[1].equals("1")){
                    mConnectedThread.write("2");    // Send "0" via Bluetooth
                    Toast.makeText(getBaseContext(), "Switching button 2 off", Toast.LENGTH_SHORT).show();
                }

            }
        });
        switch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state[2].equals("0")) {
                    mConnectedThread.write("5");    // Send "0" via Bluetooth
                    Toast.makeText(getBaseContext(), "Switching button 3 on", Toast.LENGTH_SHORT).show();
                }
                if(state[2].equals("1")){
                    mConnectedThread.write("4");    // Send "0" via Bluetooth
                    Toast.makeText(getBaseContext(), "Switching button 3 off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switch1voice.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                                            try {
                                                startActivityForResult(i, REQUEST_OK);
                                            } catch (Exception e) {
                                                Toast.makeText(bluetooth.this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
                                            }
                                        }


                                    }
        );

    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bluetooth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(id==android.R.id.home){

            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);


    }
    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA

        //address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OK && resultCode == RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            // ((TextView) findViewById(R.id.text1)).setText(thingsYouSaid.get(0));
            Toast.makeText(this, thingsYouSaid.get(0), Toast.LENGTH_LONG).show();
            if((thingsYouSaid.get(0).equals(voice1_on))&&(state[0].equals("0"))){
                //Toast.makeText(this, thingsYouSaid.get(0), Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "Turn on led", Toast.LENGTH_LONG).show();
                voicestate[0]=1;
            }
             if((thingsYouSaid.get(0).equals(voice1_off))&&(state[0].equals("1"))){
               // Toast.makeText(this, thingsYouSaid.get(0), Toast.LENGTH_LONG).show();
               // Toast.makeText(this, "Turn off led", Toast.LENGTH_LONG).show();
                voicestate[0]=0;
             }



            if((thingsYouSaid.get(0).equals(voice2_on))&&(state[1].equals("0"))){
               // Toast.makeText(this, thingsYouSaid.get(0), Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "Turn on led", Toast.LENGTH_LONG).show();
                voicestate[1]=1;
            }
             if((thingsYouSaid.get(0).equals(voice2_off))&&(state[1].equals("1"))){
               // Toast.makeText(this, thingsYouSaid.get(0), Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "Turn off led", Toast.LENGTH_LONG).show();
                voicestate[1]=0;

            }

            if((thingsYouSaid.get(0).equals(voice3_on))&&(state[2].equals("0"))){
              //  Toast.makeText(this, thingsYouSaid.get(0), Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "Turn on led", Toast.LENGTH_LONG).show();
                voicestate[2]=1;
            }
            if((thingsYouSaid.get(0).equals(voice3_off))&&(state[2].equals("1"))){
               // Toast.makeText(this, thingsYouSaid.get(0), Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "Turn off led", Toast.LENGTH_LONG).show();
                voicestate[2]=0;

            }

        }
    }
    @Override
    public void onPause() {
        super.onPause();
        try {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();


            }
        }
    }
}
