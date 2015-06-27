#include <SPI.h>
#include <Ethernet.h>

#include <avr/sleep.h>
#include <avr/power.h>


int bulb_1 =8;
int bulb_2=9;
int bulb_3=7;
int btn_1=4;
int btn_2=5;
int btn_3=6;

int btnstate_1=0,btnstate_2=0,btnstate_3=0;
int state_bulb_1,state_bulb_2,state_bulb_3;
 
byte mac[] = { 0x08, 0x00, 0x27, 0x00, 0xA8, 0x3F };
IPAddress ip(192,168,100,11); 
byte gateway[] = { 192, 168, 100, 1 };                  
byte subnet[] = { 255, 255, 255, 0 };
EthernetServer server(80); 

String readString;
 
//Char used for reading in Serial characters
char inbyte = 0;

 
void setup() {
   // disable ADC
  ADCSRA = 0;  

  // turn off various modules
  
 //power_all_disable ();
 power_usart0_enable();
  power_spi_enable();
  power_twi_enable();
  //power_adc_enable();
  power_timer0_enable();
  set_sleep_mode (SLEEP_MODE_IDLE);  
  noInterrupts ();           // timed sequence follows
  sleep_enable();
 
  // turn off brown-out enable in software
  MCUCR = bit (BODS) | bit (BODSE);
  MCUCR = bit (BODS); 
  interrupts ();             // guarantees next instruction executed
  sleep_cpu ();              // sleep within 3 clock cycles of above    
  
  // initialise serial communications at 9600 bps:
  Serial.begin(9600);
  Ethernet.begin(mac, ip, gateway, subnet);
  server.begin();

  pinMode(bulb_1,OUTPUT);
  pinMode(bulb_2,OUTPUT);
  pinMode(bulb_3,OUTPUT);
  pinMode(btn_1,INPUT);
  pinMode(btn_2,INPUT);
  pinMode(btn_3,INPUT);
  

}
 
void loop() {
  readSensors();
  sendAndroidValues();
  //when serial values have been received this will be true
  
  
  ///////////////////////////////BLUETOOTH CHANGE OF DATA ////////////////////////////////////
  if (Serial.available() > 0)
  {
    inbyte = Serial.read();
    if (inbyte == '0')
    {
      //LED off
      digitalWrite(bulb_1,LOW);
    }
    if (inbyte == '1')
    {
      //LED on
      digitalWrite(bulb_1,HIGH);
    }
    if (inbyte == '2')
    {
      //LED off
      digitalWrite(bulb_2,LOW);
    }
    if (inbyte == '3')
    {
      //LED on
      digitalWrite(bulb_2,HIGH);
    }if (inbyte == '4')
    {
      //LED off
      digitalWrite(bulb_3,LOW);
    }
    if (inbyte == '5')
    {
      //LED on
      digitalWrite(bulb_3,HIGH);
    }
  }
  //////////////////////////////////////////////////////////////////////////////////////
  ////////////////////// BUTTON CHANGE OF DATA /////////////////////////////////////////
  
  btnstate_1 = digitalRead(btn_1);
  if(btnstate_1 ==HIGH)
  {
    if(state_bulb_1== HIGH)
    {
      digitalWrite(bulb_1,LOW);
    }
    else if(state_bulb_1 ==LOW)
    {
      digitalWrite(bulb_1,HIGH);
    }
  }
    btnstate_2 = digitalRead(btn_2);
  if(btnstate_2 ==HIGH)
  {
    if(state_bulb_2 == HIGH)
    {
      digitalWrite(bulb_2,LOW);
    }
    else if(state_bulb_2 ==LOW)
    {
      digitalWrite(bulb_2,HIGH);
    }
  }
    btnstate_3 = digitalRead(btn_3);
    if(btnstate_3 ==HIGH)
  {
    if(state_bulb_3 == HIGH)
    {
      digitalWrite(bulb_3,LOW);
    }
    else if(state_bulb_3 ==LOW)
    {
      digitalWrite(bulb_3,HIGH);
    }
  }
  
  
  ////////////////////////////////////////////////////////////////////////////////////
  //delay by 2s. Meaning we will be sent values every 2s approx
  //also means that it can take up to 2 seconds to change LED state
  delay(300);
  
  ////////////////////////////////////////////////////////////////////////////////////
  EthernetClient client = server.available();
  if (client) {  
    while (client.connected()) {
      if (client.available()) {   
        char c = client.read();
        if (readString.length() < 100) {
            readString += c;
           }
        
        Serial.println(readString);
        if (c == '\n') {

          client.println("HTTP/1.1 200 OK"); //send new page
          client.println("Content-Type: text/html");
          client.println();
          client.println("<HTML>");
          client.println("<HEAD>");
          client.println("<meta name='apple-mobile-web-app-capable' content='yes' />");
          client.println("<meta name='apple-mobile-web-app-status-bar-style' content='black-translucent' />");
          client.println("<meta http-equiv='refresh' content='5'>");
          //client.println("<meta http-equiv=\"/refresh\"\" content=\"/5; URL='http://192.168.100.19'\"\">");
          client.println("<link rel='stylesheet' type='text/css' href='http://homepages.iitb.ac.in/~14d070044/Third.css' />");
          client.println("<TITLE>Arduino Control Online</TITLE>");
          client.println("</HEAD>");
          client.println("<BODY>");
          client.println("<H1>TechnoHome</H1>");
          client.println("<hr />");
          client.println("<div id='buttons'>");
          client.println("<a href=\?button1on class='btn blue'>Button 1 ON</a>");
          client.println("<a href=\?button1off class='btn blue'>Button 1 OFF</a>");
          client.println("<H3>");
          if (readString.indexOf("?button1on") > 0) {
            digitalWrite(bulb_1, HIGH);
            //btn=1;
          }
          if (readString.indexOf("?button1off") > 0) {
            digitalWrite(bulb_1, LOW);
            //btn=0;
          }
          readSensors();
          client.println("Appliance 1");
          if(state_bulb_1==0){client.println("OFF");}
          else{client.println("ON");};
          client.println("</H3>");
          //client.println("<br />");
          //client.println("<br />");
          client.println("<a href=\?button2on class='btn blue'>Button 2 ON</a>");
          client.println("<a href=\?button2off class='btn blue'>Button 2 OFF</a>");
          client.println("<H3>");
          
          if (readString.indexOf("?button2on") > 0) {
            digitalWrite(bulb_2, HIGH);
            //btn=1;
          }
          if (readString.indexOf("?button2off") > 0) {
            digitalWrite(bulb_2, LOW);
            //btn=0;
          }
          readSensors();
          client.println("Appliance 2");
          if(state_bulb_2==0){client.println("OFF");}
          else{client.println("ON");};
          client.println("</H3>");
          //client.println("<br />");         
          //client.println("<br />");
          client.println("<a href=\?button3on class='btn blue'>Button 3 ON</a>");
          client.println("<a href=\?button3off class='btn blue'>Button 3 OFF</a>");
          client.println("<H3>");
          
          client.println("Appliance 3");
          if (readString.indexOf("?button3on") > 0) {
            digitalWrite(bulb_3, HIGH);
            //btn=1;
          }
          if (readString.indexOf("?button3off") > 0) {
            digitalWrite(bulb_3, LOW);
            //btn=0;
          }
          readSensors();
          if(state_bulb_3==0){client.println("OFF");}
          else{client.println("ON");};
          client.println("</H3>");
          //client.println("<br />");
          //client.println("<br />");
          client.println("</div>");
          
          ///////////////////////////////////////////
          
            
          readString = "";
          
          client.println("</body>");
          client.println("</html>");
          
          
          
          
          delay(100);
          /*  break;

          // every line of text received from the client ends with \r\n
          if (c == '\n') {
            // last character on line of received text
            // starting new line with next character read
            currentLineIsBlank = true;
          }
          else if (c != '\r') {
            // a text character was received from client
            currentLineIsBlank = false;
          }
          // end if (client.available())
          // end while (client.connected())
          delay(1);      // give the web browser time to receive the data*/
          client.stop();
          
         

          // close the connection
          
        }
      }
    }
  }
}
 
void readSensors()
{
  state_bulb_1 = digitalRead(bulb_1);
  state_bulb_2 = digitalRead(bulb_2);
  state_bulb_3 = digitalRead(bulb_3);
}
//sends the values from the sensor over serial to BT module


void sendAndroidValues()
 {
  //puts # before the values so our app knows what to do with the data
  Serial.print('#');
  Serial.print(state_bulb_1);
  Serial.print('+');
  Serial.print(state_bulb_2);
  Serial.print('+');
  Serial.print(state_bulb_3);
  Serial.print('~'); //used as an end of transmission character - used in app for string length
  Serial.println();
   
 delay(10);        //added a delay to eliminate missed transmissions
}
