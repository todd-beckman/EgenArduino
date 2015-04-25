int framedelay = 1000;

int servopos = 0;
int serverpin = 5;
Servo servo;

int buttonpin = 8;
int clockwise = 0;

void setup() {
  Serial.begin(9600);
  servo.attach(servopin);
  pinMode(buttonpin, INPUT);
}

void loop() {
  //print out the analog read to serial
  Serial.print("Analog says: ");
  Serial.println(analogRead(A0));
  //print out the voltage read to serial
  Serial.print("Voltage says: ");
  Serial.println(analogRead(A1) * (5.0 / 1023.0));
  //move the servo alternating left and right
  if (digitalRead(buttonpin) == HIGH) {
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
  delay(framedelay);
}
