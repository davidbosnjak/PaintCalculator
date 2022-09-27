import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import java.math.*;
public class PaintCalculatorProgram2 {
    public static void main(String[] args) throws InterruptedException{
        final short SQUARE_CM_IN_SQUARE_M = 10000;
        final short METERS_PER_PAINT = 11;
        final double PAINT_IN_CAN = 3.78;
        final short SEMIGLOSS_PER_WALL = 2;
        final short PRIMER_PER_WALL = 1;

        JOptionPane pane = new JOptionPane();
        ProgressMonitor progressBar = new ProgressMonitor(null, "Calculating paint needed", "note", 0,100);
        String sLength = pane.showInputDialog("Please enter the length of your room");
        String sWidth = pane.showInputDialog("Please enter the width of your room");
        String sHeight = pane.showInputDialog("Please enter the height of your room");



        short roomLength = Short.parseShort(sLength);

        short roomWidth = Short.parseShort(sWidth);

        short roomHeight = Short.parseShort(sHeight);

        int answerCM = getWallArea(roomLength, roomWidth, roomHeight);

        double answerM = answerCM/SQUARE_CM_IN_SQUARE_M;

        double paintLitresRequired = answerM / METERS_PER_PAINT;
        BigDecimal paintLitresRequiredRounded = new BigDecimal(paintLitresRequired).setScale(2,RoundingMode.HALF_UP);
        double dPaintLitresRequiredRounded = paintLitresRequiredRounded.doubleValue();

        double paintCansRequiredDecimal = paintLitresRequired / PAINT_IN_CAN;
        double primerCansRequiredDecimal = paintCansRequiredDecimal * PRIMER_PER_WALL;
        double semiCansRequiredDecimal = paintCansRequiredDecimal * SEMIGLOSS_PER_WALL;

        int semiCansRequiredInt = (int) Math.ceil(semiCansRequiredDecimal);


        int primerCansRequiredInt  = (int) Math.ceil(primerCansRequiredDecimal);
/*
        System.out.println("You will need "+primerCansRequiredInt+" cans of primer");
        System.out.println("Your calculations for:");
        System.out.println("Length: "+roomLength+"\nWidth: "+roomWidth+"\nHeight: "+roomHeight+"\n");
        System.out.println("Total surface area to be painted: "+answerCM+"cm^2, "+answerM+"m^2");
        System.out.println("Litres of paint required per coat: "+paintLitresRequired);
        System.out.println("Cans of primer needed: "+primerCansRequiredInt+"\nCans of semi-gloss needed: "+semiCansRequiredInt);
*/
        String sIntro = "Your calulations for: \n length: "+roomLength+" width: "+roomWidth+" height: "+roomHeight+"\n";
        String sSurfaceArea = "Total surface area to be painted: "+ answerCM+"cm\u00B2 or "+answerM+"m\u00B2\n";
        String sLitres = "Litres of paint required per coat: "+dPaintLitresRequiredRounded+"L\n";
        String sCans = "Cans of primed required: "+primerCansRequiredInt+"\nCans of semi-gloss required: "+semiCansRequiredInt;
        for(int i =0; i<=100; i++){
            Thread.sleep(100);
            progressBar.setProgress(i);
            if(i<=30){
                progressBar.setNote("Calculating surface area...");
            }
            else if(i>30 && i<=60){
                progressBar.setNote("Calculating litres of paint per coat...");

            }
            else{
                progressBar.setNote("Calculating cans required...");
            }

        }
        pane.showMessageDialog(null, sIntro+sSurfaceArea+sLitres+sCans);
        System.out.println(paintLitresRequired);



    }
    public static int getWallArea(int length, int width, int height){
        int returnValue = length*height*2+ width*height*2;
        return returnValue;



    }
}
