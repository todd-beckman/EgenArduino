#include <Servo.h>

int framedelay = 1000;

int servopos = 0;
int servopin = 5;

Servo servo;

int buttonpin = 8;
int clockwise = 0;

void setup() {
  Serial.begin(9600);
  servo.attach(servopin);
  pinMode(buttonpin, INPUT);
}

void loop() {
  //get input
  int analog0 = analogRead(A0);
  int analog1 = analogRead(A1);
  int buttonvalue = digitalRead(buttonpin);
  
  //update
  //move the servo alternating left and right
  if (buttonvalue == HIGH) {
    if (clockwise == 1) {
      for (servopos = 0; servopos < 180; servopos += 1) {
        servo.write(servopos);
        delay(15);
      }
      clockwise = 0;
    }
    else {
      for (servopos = 0; servopos < 180; servopos += 1) {
        servo.write(servopos);
        delay(15);
      }
      clockwise = 1;
    }
  }
  
  //output
    //print out the analog read to serial
  Serial.print("Analog says: ");
  Serial.println(analog0);
  //print out the voltage read to serial
  Serial.print("Voltage says: ");
  Serial.println(analog1 * (5.0 / 1023.0));
  delay(framedelay);
}
