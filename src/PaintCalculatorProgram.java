import java.util.Scanner;

public class PaintCalculatorProgram {

    public static void main(String[] args) {
        boolean continueProgram = true;
        while(continueProgram){
            paintCalculator();
            System.out.println("Do you want to make another calculation? y/n");
            char userSelection = new Scanner(System.in).next().charAt(0);
            if(userSelection== 'y' || userSelection == 'Y'){ continueProgram = true;}
            else{continueProgram = false;}
        }
    }
    public static void paintCalculator(){
        //Declaring constants
        final double SQUARE_CM_IN_SQUARE_M = 10000;
        final int METERS_PER_PAINT = 11;
        final double PAINT_IN_CAN = 3.78f;
        final int SEMIGLOSS_PER_WALL = 2;
        final int PRIMER_PER_WALL = 1;

        //Getting user inputs for dimensions of the room
        System.out.println("Please enter length of the room");
        int roomLength = checkInput();
        System.out.println("Please enter width of the room");
        int roomWidth = checkInput();
        System.out.println("Please enter height of the room");
        int roomHeight = checkInput();

        //Get the surface area on the walls based on the input
        int answerCM = getWallArea(roomLength, roomWidth, roomHeight);

        //use conversion factors to get all the desired values
        double answerM = answerCM/SQUARE_CM_IN_SQUARE_M;
        double paintLitresRequired = answerM / METERS_PER_PAINT;
        double paintCansRequiredDecimal = paintLitresRequired / PAINT_IN_CAN;
        double primerCansRequiredDecimal = paintCansRequiredDecimal * PRIMER_PER_WALL;
        double semiCansRequiredDecimal = paintCansRequiredDecimal * SEMIGLOSS_PER_WALL;

        //finding the ceiling of the cans required because you cannot buy a fraction of a can of paint, you will need a full one
        int semiCansRequiredInt = (int) Math.ceil(semiCansRequiredDecimal);
        int primerCansRequiredInt  = (int) Math.ceil(primerCansRequiredDecimal);

        //rounding to 3 significant digits
        paintLitresRequired = roundDouble(paintLitresRequired, 3);

        //breaking up the output into different strings
        String sSurfaceArea = "Total surface area to be painted: "+ answerM+"m\u00B2";
        String sLitres = "Litres of paint required per coat: "+paintLitresRequired+"L";
        String sIntro = "Your calculations for: \nlength: "+roomLength+" width: "+roomWidth+" height: "+roomHeight+"";
        String sCans = "Cans of primed required: "+primerCansRequiredInt+"\nCans of semi-gloss required: "+semiCansRequiredInt;

        //if the area is a whole number, remove the redundant decimal at the end
        if(answerM == Math.floor(answerM)){
            int roundedSurfaceArea = (int)Math.floor(answerM);
            sSurfaceArea = "Total surface area to be painted: "+ roundedSurfaceArea+"m"+"\u00B2";
        }
        //If the volume is a whole number remove the decimal at the end
        if(paintLitresRequired == Math.floor(paintLitresRequired)){
            int intPaintLitresRequired = (int)Math.floor(paintLitresRequired);
            sLitres = "Litres of paint required per coat: "+intPaintLitresRequired+"L";

        }
        //print out all the strings in order
        System.out.println(sIntro);
        System.out.println(sSurfaceArea);
        System.out.println(sLitres);
        System.out.println(sCans);
    }
    public static short checkInput(){
        short parsedNumber;

        //infinite loop until an appropriate value is inputted by the user
        while(true){
            //setup scanner object to read next line from user
            Scanner scanner = new Scanner(System.in);
            String userInput =scanner.nextLine();

            //if a short can successfully be parsed from the input string without error, it will break out of the loop. Otherwise, it will keep asking for a correct input and showing an error message
            try{
                parsedNumber = Short.parseShort(userInput);
                if(parsedNumber <0){
                    System.out.println("No negative numbers!");
                }
                else {
                    break;
                }
            }
            //If the inputted value is not a number let the user know
            catch(NumberFormatException e){
                System.out.println("Error! "+userInput+" is not a valid number!");
            }
        }
        return parsedNumber;
    }
    public static double roundDouble(double numToRound, int digitsToShow){
        //This condition exists because if 0 is picked, there will be an infinite loop as for {XeR}, 0*X = 0
        if(numToRound == 0) return 0;

        //This method works by multiplying a given decimal by 10 until it's an x digit number, once it's an x digit number we floor it, and we're left with only x digits.
        //Then we divide it by the same number we multiplied it by, and we're left with a decimal of x significant digits
        final int FACTOR = 10;
        int count = 1;

        int intNumToRound;
        while(numToRound<Math.pow(10,digitsToShow-1)){
            numToRound *=FACTOR;
            count++;
        }

        intNumToRound= (int)Math.floor(numToRound);
        numToRound = intNumToRound;


        numToRound/=(Math.pow(FACTOR,count-1));


        return numToRound;
    }
    //method that calculates the surface area of the walls based on the dimensions
    public static int getWallArea(int length, int width, int height){
        return length*height*2+ width*height*2;

    }
}
