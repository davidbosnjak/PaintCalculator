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

        //JOptionPane and ProgressMonitor objects being initialized
        JOptionPane pane = new JOptionPane();
        ProgressMonitor progressBar = new ProgressMonitor(null, "Calculating paint needed", "note", 0,100);

        //declaring input windows to get room dimensions
        String sLength = pane.showInputDialog("Please enter the length of your room");
        String sWidth = pane.showInputDialog("Please enter the width of your room");
        String sHeight = pane.showInputDialog("Please enter the height of your room");

        //parsing the strings from the input windows into shorts
        short roomLength = Short.parseShort(sLength);
        short roomWidth = Short.parseShort(sWidth);
        short roomHeight = Short.parseShort(sHeight);

        //using the gettWallArea function to return the surface area of the relevant walls. answer is returned in square centimeters
        int answerCM = getWallArea(roomLength, roomWidth, roomHeight);

        //converting square cm to square meters using a constant conversion factor
        double answerM = answerCM/SQUARE_CM_IN_SQUARE_M;

        //using another conversion factor to find litres of paint required. some work went into rounding this value. i found a class called BigDecimal. the constructor takes in a double and it has several methods and rounding modes
        double paintLitresRequired = answerM / METERS_PER_PAINT;

        //I chose the rounding mode HALF_UP because it is the most commonly used system in schools
        BigDecimal paintLitresRequiredRounded = new BigDecimal(paintLitresRequired).setScale(2,RoundingMode.HALF_UP);
        double dPaintLitresRequiredRounded = paintLitresRequiredRounded.doubleValue();

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
        String sLitres = "Litres of paint required per coat: "+dPaintLitresRequiredRounded+"L\n";
        String sCans = "Cans of primed required: "+primerCansRequiredInt+"\nCans of semi-gloss required: "+semiCansRequiredInt;

        //using a for loop going from 0 to 100 to set the progress on my progress bar. using a delay of 100ms for each percentage completion.
        for(int i =0; i<=100; i++){
            Thread.sleep(100);
            progressBar.setProgress(i);

            //setting a different note based on what percentage of progress has been completed
            if(i<=30){
                progressBar.setNote("Calculating surface area...");
            }
            else if(i>30 && i<=60){
                progressBar.setNote("Calculating litres per coat..");
            }
            else{
                progressBar.setNote("Calculating cans required...");
            }
        }

        //showing a message containing all the strings after the progress bar has completed
        pane.showMessageDialog(null, sIntro+sSurfaceArea+sLitres+sCans);

    }
    //method to get the surface area of the walls based on dimensions of the room
    public static int getWallArea(int length, int width, int height){
        int returnValue = length*height*2+ width*height*2;
        return returnValue;



    }
}
