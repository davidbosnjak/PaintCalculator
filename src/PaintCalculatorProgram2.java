import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import java.math.*;
public class PaintCalculatorProgram2 {
    public static void main(String[] args) throws InterruptedException{

        //declaring constants instead of hardcoding them
        final short SQUARE_CM_IN_SQUARE_M = 10000;
        final short METERS_PER_PAINT = 11;
        final double PAINT_IN_CAN = 3.78;
        final short SEMIGLOSS_PER_WALL = 2;
        final short PRIMER_PER_WALL = 1;

        //JProgressMonitor object being initialized
        //I did not make a JOptionPane object as most of its methods are static and no object is required
        ProgressMonitor progressBar = new ProgressMonitor(null, "Calculating paint needed", "note", 0,100);

        //parsing the strings from the input windows into shorts
        short roomLength = checkJOptionInput("Please enter the length of your room");
        short roomWidth = checkJOptionInput("Please enter the width of your room");
        short roomHeight = checkJOptionInput("Please enter the height of your room");

        //using the getWallArea function to return the surface area of the relevant walls. answer is returned in square centimeters
        //the reason this has to be a double is that answerM below it can have decimals, and in division, at least one variable needs to be a double to get a double as the answer
        double answerCM = getWallArea(roomLength, roomWidth, roomHeight);

        //converting square cm to square meters using a constant conversion factor
        double answerM = answerCM/SQUARE_CM_IN_SQUARE_M;

        //rounding to 2 decimal places
        answerM = roundDouble(answerM,2);

        //using another conversion factor to find litres of paint required. some work went into rounding this value. I found a class called BigDecimal. the constructor takes in a double, and it has several methods and rounding modes
        double paintLitresRequired = answerM / METERS_PER_PAINT;

        //rounding to 2 decimal places
        double dPaintLitresRequired = roundDouble(paintLitresRequired,2);

        //More conversion factors to get desired values.
        double paintCansRequiredDecimal = paintLitresRequired / PAINT_IN_CAN;
        double primerCansRequiredDecimal = paintCansRequiredDecimal * PRIMER_PER_WALL;
        double semiCansRequiredDecimal = paintCansRequiredDecimal * SEMIGLOSS_PER_WALL;

        //finding the ceiling of the cans required because you cannot buy a fraction of a can of paint, you will need a full one
        int semiCansRequiredInt = (int) Math.ceil(semiCansRequiredDecimal);
        int primerCansRequiredInt  = (int) Math.ceil(primerCansRequiredDecimal);

        //breaking up the output into different strings representing a different part of the message
        String sIntro = "Your calculations for: \n length: "+roomLength+" width: "+roomWidth+" height: "+roomHeight+"\n";
        String sSurfaceArea = "Total surface area to be painted: "+ answerCM+"cm\u00B2 or "+answerM+"m\u00B2\n";
        String sLitres = "Litres of paint required per coat: "+dPaintLitresRequired+"L\n";
        String sCans = "Cans of primed required: "+primerCansRequiredInt+"\nCans of semi-gloss required: "+semiCansRequiredInt;

        //using a for loop going from 0 to 100 to set the progress on my progress bar. using a delay of 100ms for each percentage completion.
        for(int i =0; i<=100; i++){
            Thread.sleep(100);
            progressBar.setProgress(i);

            //setting a different note based on what percentage of progress has been completed
            if(i<=30){
                progressBar.setNote("Calculating surface area...");
            }
            else if(i<=60){
                progressBar.setNote("Calculating litres per coat..");
            }
            else{
                progressBar.setNote("Calculating cans required...");
            }
        }

        //showing a message containing all the strings after the progress bar has completed
        JOptionPane.showMessageDialog(null, sIntro+sSurfaceArea+sLitres+sCans, "Calculations", JOptionPane.INFORMATION_MESSAGE);

    }
    //method to get the surface area of the walls based on dimensions of the room
    public static double getWallArea(int length, int width, int height){
        return length*height*2+ width*height*2;
    }
    //method used for error checking inputs in JOptionPane dialog boxes. Use message parameter to set a message for input
    public static short checkJOptionInput(String message){
        short parsedNumber;

        //infinite loop until an appropriate value is inputted by the user
        while(true){


            String userInput = JOptionPane.showInputDialog(null, message);

            //if a short can successfully be parsed from the input string without error, it will break out of the loop. Otherwise, it will keep asking for a correct input and showing an error message
            try{
                parsedNumber = Short.parseShort(userInput);
                if(parsedNumber <0){
                    JOptionPane.showMessageDialog(null, "No negative numbers! You can't have a room with negative side lengths!","Error!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    break;
                }
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Enter a valid number! \n"+userInput+" is not a valid number!","Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        return parsedNumber;
    }
    //method to round a double to a certain degree of precision
    public static double roundDouble(double numToRound, int digitsToShow){

        //using the BigDecimal class to round a double to a variable degree of precision and setting the rounding mode to HALF_UP which is the standard system taught in schools
        BigDecimal bgNumToRound = new BigDecimal(numToRound);
        bgNumToRound = bgNumToRound.round(new MathContext(3));

        //converting the BigDecimal object to a double and returning it
        return bgNumToRound.doubleValue();
    }
}
