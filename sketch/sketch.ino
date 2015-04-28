#include <Time.h>
#include <Servo.h>

int times[] = {0, 6000, 9000, 11250};
int NOLITER = 0;
int HALFLITER = 1;
int THREEQUARTERS = 2;
int ONELITER = 3;


int framedelay = 1000;
int framecount = 0;

int servopos = 0;
int servopin = 5;
Servo servo;


int closea = 70;
int opena = 110;

int buttonpin = 8;
int buttondown = 1;
time_t buttonstart;
time_t buttonelapsed;
int clockwise = 0;

time_t start = now();
time_t lastcheck = now();

void setup() {
  Serial.begin(9600);
  servo.attach(servopin);
  pinMode(buttonpin, INPUT);
  
  buttonstart = now();
  servo.write(closea);
}

float salinity = 0;
float temperature = 0;

void dispense(int amount) {
  servo.write(opena);
  delay(amount);
  servo.write(closea);
  
}

float getVoltage(int pin)
{
  // This function has one input parameter, the analog pin number
  // to read. You might notice that this function does not have
  // "void" in front of it; this is because it returns a floating-
  // point value, which is the true voltage on that pin (0 to 5V).
  
  // You can write your own functions that take in parameters
  // and return values. Here's how:
  
    // To take in parameters, put their type and name in the
    // parenthesis after the function name (see above). You can
    // have multiple parameters, separated with commas.
    
    // To return a value, put the type BEFORE the function name
    // (see "float", above), and use a return() statement in your code
    // to actually return the value (see below).
  
    // If you don't need to get any parameters, you can just put
    // "()" after the function name.
  
    // If you don't need to return a value, just write "void" before
    // the function name.

  // Here's the return statement for this function. We're doing
  // all the math we need to do within this statement:
  
  return (analogRead(pin) * 0.004882814);
  
  // This equation converts the 0 to 1023 value that analogRead()
  // returns, into a 0.0 to 5.0 value that is the true voltage
  // being read at that pin.
}

void loop() {
  framecount++;
  /*
  salinity += analogRead(A0);
  temperature += analogRead(A1) / 10;
  */
  salinity = analogRead(A0);
  float voltage = getVoltage(A1);
  temperature = (voltage - 0.5) * 100.0;
  int buttonvalue = digitalRead(buttonpin);  
  
  //  Check to see how much time has passed
  time_t present = now();
  
  //  See how long it's been since the start
  time_t elapsed = present - start;
  
  //  see if it's been at least 10 seconds since the last check
  if (2 < elapsed - lastcheck) {
    
    //  save the current elapsed as the last check
    lastcheck = elapsed;
    //  find averages
    /*
    salinity /= framecount;
    temperature /= framecount;
    */
    
    //  Print everything
    Serial.print(present);
    Serial.print(",");
    Serial.print(salinity * (5.0 / 1023.0));
    Serial.print(",");
    Serial.println(temperature);
    
    salinity = 0;
    temperature = 0;
    framecount = 0;
  }
}
