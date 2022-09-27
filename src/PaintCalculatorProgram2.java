import java.util.Scanner;
import javax.swing.JOptionPane;
public class PaintCalculatorProgram2 {
    public static void main(String[] args) {
        final short SQUARE_CM_IN_SQUARE_M = 10000;
        short METERS_PER_PAINT = 11;
        double PAINT_IN_CAN = 3.78f;
        short SEMIGLOSS_PER_WALL = 2;
        short PRIMER_PER_WALL = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter length of the room");
        short roomLength = scanner.nextShort();
        System.out.println("Please enter height of the room");
        short roomWidth = scanner.nextShort();
        System.out.println("Please enter height of the room");
        short roomHeight = scanner.nextShort();
        int answerCM = getWallArea(roomLength, roomWidth, roomHeight);
        System.out.println(answerCM);
        double answerM = answerCM/SQUARE_CM_IN_SQUARE_M;
        System.out.println("Surface area: "+answerM);
        double paintLitresRequired = answerM / METERS_PER_PAINT;
        System.out.println("You will need "+paintLitresRequired);
        double paintCansRequiredDecimal = paintLitresRequired / PAINT_IN_CAN;
        double primerCansRequiredDecimal = paintCansRequiredDecimal * PRIMER_PER_WALL;
        double semiCansRequiredDecimal = paintCansRequiredDecimal * SEMIGLOSS_PER_WALL;

        int semiCansRequiredInt = (int) Math.ceil(semiCansRequiredDecimal);


        int primerCansRequiredInt  = (int) Math.ceil(primerCansRequiredDecimal);

        System.out.println("You will need "+primerCansRequiredInt+" cans of primer");
        System.out.println("Your calculations for:");
        System.out.println("Length: "+roomLength+"\nWidth: "+roomWidth+"\nHeight: "+roomHeight+"\n");
        System.out.println("Total surface area to be painted: "+answerCM+"cm^2, "+answerM+"m^2");
        System.out.println("Litres of paint required per coat: "+paintLitresRequired);
        System.out.println("Cans of primer needed: "+primerCansRequiredInt+"\nCans of semi-gloss needed: "+semiCansRequiredInt);




    }
    public static int getWallArea(int length, int width, int height){
        int returnValue = length*height*2+ width*height*2;
        return returnValue;



    }
}
