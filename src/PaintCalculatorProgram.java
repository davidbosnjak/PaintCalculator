import java.util.Scanner;
import java.text.DecimalFormat;
public class PaintCalculatorProgram {

    public static void main(String[] args) {
        int SQUARE_CM_IN_SQUARE_M = 10000;
        int METERS_PER_PAINT = 11;
        double PAINT_IN_CAN = 3.78f;
        int SEMIGLOSS_PER_WALL = 2;
        int PRIMER_PER_WALL = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter length of the room");
        int roomLength = scanner.nextInt();
        System.out.println("Please enter width of the room");
        int roomWidth = scanner.nextInt();
        System.out.println("Please enter height of the room");
        int roomHeight = scanner.nextInt();
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
        System.out.printf("Litres of paint required per coat: %.2f \n",paintLitresRequired);
        System.out.println("Cans of primer needed: "+primerCansRequiredInt+"\nCans of semi-gloss needed: "+semiCansRequiredInt);




    }
    public static int getWallArea(int length, int width, int height){
        int returnValue = length*height*2+ width*height*2;
        return returnValue;



    }
}
