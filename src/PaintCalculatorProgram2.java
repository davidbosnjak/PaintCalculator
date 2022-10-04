//Imports
//Everything from javax.swing because I'm using lots of classes from it
import javax.swing.*;
//Font class for making the title of the window look better
import java.awt.Font;

//classes for events and event listening. For example, button presses are an event I'm listening for
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PaintCalculatorProgram2 implements ActionListener {
    //Declaring all objects that must be accessed from multiple class methods. Buttons and menus must be accessed both from the main method and the event action method
    public static JButton submitButton = new JButton("Start Calculation");
    static String[] unitChoices = {"Meters","Centimeters","Feet","Inches"};
    static Integer[] paintChoices = {1,2,3,4,5};
    static Integer[] precisionChoices = {1,2,3,4,5,6};
    static String[] roundingChoices = {"Floor", "Half up", "Mr Lauder"};
    static JComboBox<String> unitMenu = new JComboBox<>(unitChoices);
    static String[] paintVolumeOptions = {"Default(3.78)","Custom"};
    static JComboBox<String> paintVolumeSelector = new JComboBox<>(paintVolumeOptions);
    static JComboBox<Integer> primerSelector = new JComboBox<>(paintChoices);
    static JComboBox<Integer> semiSelector = new JComboBox<>(paintChoices);
    static JComboBox<Integer> precisionSelector = new JComboBox<>(precisionChoices);
    static JComboBox<String> roundingModeSelector = new JComboBox<>(roundingChoices);
    static final double defaultPaintVolume = 3.78;
    static JPanel panel = new JPanel();

    //main method
    public static void main(String[] args){

        //Creating a new window using JFrame, setting close behavior, setting a title and size
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Paint Calculator Pro");
        frame.setSize(400,400);

        //Adding a panel to the frame. I will put objects on the panel
        frame.add(panel);

        //Not using any layouts like gridlayouts. In this program I manually set the size and position of each object
        panel.setLayout(null);

        //Main label of the window in a bigger and bolder font
        JLabel titleMessage = new JLabel("Paint Calculator Pro");
        titleMessage.setFont(new Font("Times", Font.BOLD,20));
        titleMessage.setBounds(50,20,400,25);
        panel.add(titleMessage);

        //Label that indicates that the dropdown menu is for unit selection
        JLabel unitMessage = new JLabel("Select Unit:");
        unitMessage.setBounds(30,60,150,20);
        panel.add(unitMessage);

        //Adding a dropdown menu for selecting the desired unit of measurement
        unitMenu.addActionListener(new PaintCalculatorProgram2());
        unitMenu.setBounds(30,80,120,25);
        panel.add(unitMenu);

        //Adding a button that submits the information on the page
        submitButton.addActionListener(new PaintCalculatorProgram2());
        submitButton.setBounds(200,300,150,30);
        panel.add(submitButton);

        //Adding can volume label
        JLabel paintVolumeMessage = new JLabel("Select can volume:");
        paintVolumeMessage.setBounds(30,110,150,20);
        panel.add(paintVolumeMessage);

        //Adding paint can volume dropdown menu. Also adding an a call to the action listener here because the program will need to know when the user has selected a custom paint can volume
        paintVolumeSelector.setBounds(30,130,120,20);
        paintVolumeSelector.addActionListener(new PaintCalculatorProgram2());
        panel.add(paintVolumeSelector);

        //Adding primer coats label
        JLabel primerCoats = new JLabel("Select # of primer coats");
        primerCoats.setBounds(30,160,150,20);
        panel.add(primerCoats);

        //Adding the number of primer coats selection dropdown menu
        primerSelector.setBounds(30,180,120,20);
        panel.add(primerSelector);

        //Adding a semi coats label
        JLabel semiCoats = new JLabel("Select # of semi coats");
        semiCoats.setBounds(30,210,150,20);
        panel.add(semiCoats);

        //Adding the number of semi coats selection dropdown menu
        semiSelector.setBounds(30,230,120,20);
        panel.add(semiSelector);

        //Adding the precision setting label
        JLabel precisionSetLabel = new JLabel("Sig Digs paint volume");
        precisionSetLabel.setBounds(200,60,200,20);
        panel.add(precisionSetLabel);

        //Adding the precision selection dropdown menu. Select the number of significant digits you want to see in the paint volume.
        precisionSelector.setBounds(200,80,120,20);
        panel.add(precisionSelector);

        //Adding the rounding mode label
        JLabel areaRoundingLabel = new JLabel("Rounding mode");
        areaRoundingLabel.setBounds(200,110,180,20);
        panel.add(areaRoundingLabel);

        //Adding the rounding mode selection menu for the area you'll see in the unit of your choice. Options are either double or integer.
        roundingModeSelector.setBounds(200,130, 120,20);
        panel.add(roundingModeSelector);


        //Setting the default values for the dropdown menus so Mr. Lauder doesn't have to mess with the dropdowns to test the table data.
        semiSelector.setSelectedItem(2);
        precisionSelector.setSelectedItem(3);
        roundingModeSelector.setSelectedItem(roundingChoices[1]);

        //making the frame visible
        frame.setVisible(true);


    }
    //method to get the surface area of the walls based on dimensions of the room
    public static double getWallArea(int length, int width, int height){
        return length*height*2+ width*height*2;
    }
    //method used for error checking inputs in JOptionPane dialog boxes. Use message parameter to set a message for input
    public static double checkJOptionInput(String message, int mode, double defaultValue){

        double parsedNumber;
        //infinite loop until an appropriate value is inputted by the user
        while(true){

            //Showing a JOptionInput screen with the desired message
            String userInput = JOptionPane.showInputDialog(null, message);

            //If the user clicked on cancel or the x button the default value gets entered
            if(userInput == null){
                parsedNumber = (short)defaultValue;
                break;
            }
            //if a short can successfully be parsed from the input string without error, it will break out of the loop. Otherwise, it will keep asking for a correct input and showing an error message
            try{
                //this function has 2 modes, one for strictly whole numbers and one for doubles
                if(mode ==0) {
                    parsedNumber = Short.parseShort(userInput);
                }
                else{
                   parsedNumber = Double.parseDouble(userInput);
                }
                //checking for negative numbers since having them would make no sense in any context
                if(parsedNumber <0){
                    JOptionPane.showMessageDialog(null, "No negative numbers! You can't have a room with negative side lengths!","Error!", JOptionPane.ERROR_MESSAGE);
                }
                //if everything goes well, break out of the loop
                else {
                    break;
                }
            }
            //If the user did not input the desired type show an error message
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Enter a valid number! \n"+userInput+" is not a valid number!","Error!", JOptionPane.ERROR_MESSAGE);
            }
            //If anything else weird happened show an error message
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Exception occurred!","Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        return parsedNumber;
    }
    //method to round a double to a certain degree of precision
    public static double roundDouble(double numToRound, int digitsToShow, int roundingMode){
        //This condition exists because if 0 is picked, there will be an infinite loop as for {XeR}, 0*X = 0
        if(numToRound == 0) return 0;

        //This method works by multiplying a given decimal by 10 until it's an x digit number, once it's an x digit number we floor it, and we're left with only x digits.
        //Then we divide it by the same number we multiplied it by, and we're left with a decimal of x significant digits
        final int FACTOR = 10;
        int count = 1;

        while(numToRound<Math.pow(10,digitsToShow-1)){
            numToRound *=FACTOR;
            count++;
        }
        int intNumToRound = (int)numToRound;

        if(roundingMode == 0){
            System.out.println(numToRound);
            intNumToRound= (int)Math.floor(numToRound);
            System.out.println(intNumToRound);
        }
        else if(roundingMode == 1){
            intNumToRound = (int)Math.round(numToRound);
        }
        else{
            double fourDigitRound = numToRound*FACTOR;
            double fourDigitInt = intNumToRound*FACTOR;
            fourDigitRound = Math.floor(fourDigitRound);
            if(fourDigitRound-fourDigitInt >=7){
                fourDigitRound/=FACTOR;
                fourDigitRound++;
                intNumToRound = (int)fourDigitRound;
            }
            else{
                fourDigitRound /=FACTOR;
                intNumToRound = (int)fourDigitRound;
            }


        }

        numToRound = intNumToRound;
        numToRound/=(Math.pow(FACTOR,count-1));
        System.out.println(numToRound);
        return numToRound;
    }
    public static void openPaintProgram(int unit, double canVolume, int primerPerWall, int semiPerWall, int sigDigs, int roundingMode) {
        //declaring constants instead of hardcoding them
        final short SQUARE_CM_IN_SQUARE_M = 10000;
        final double SQUARE_FEET_IN_SQUARE_M = 10.7639;
        final short SQUARE_INCH_IN_SQUARE_M = 1550;
        final short METERS_PER_PAINT = 11;

        //Constants chosen by the user
        final double PAINT_IN_CAN = canVolume;
        final int SEMIGLOSS_PER_WALL = semiPerWall;
        final int PRIMER_PER_WALL = primerPerWall;



        //parsing the strings from the input windows into shorts
        short roomLength = (short) checkJOptionInput("Please enter the length of your room in CM",0,0);
        short roomWidth = (short) checkJOptionInput("Please enter the width of your room in CM",0,0);
        short roomHeight = (short) checkJOptionInput("Please enter the height of your room in CM",0,0);

        //using the getWallArea function to return the surface area of the relevant walls. answer is returned in square centimeters
        double answerCM = getWallArea(roomLength, roomWidth, roomHeight);

        //converting square cm to square meters using a constant conversion factor
        double answerM = answerCM/SQUARE_CM_IN_SQUARE_M;

        //rounding to 3 significant digits
        answerM = roundDouble(answerM, sigDigs, roundingMode);

        //using another conversion factor to find litres of paint required.
        double paintLitresRequired = answerM / METERS_PER_PAINT;

        //rounding to 3 sig digs
        double PaintLitresRequired = roundDouble(paintLitresRequired,sigDigs, roundingMode);

        //More conversion factors to get desired values.
        double paintCansRequiredDecimal = paintLitresRequired / PAINT_IN_CAN;
        double primerCansRequiredDecimal = paintCansRequiredDecimal * PRIMER_PER_WALL;
        double semiCansRequiredDecimal = paintCansRequiredDecimal * SEMIGLOSS_PER_WALL;

        //finding the ceiling of the cans required because you cannot buy a fraction of a can of paint, you will need a full one
        int semiCansRequiredInt = (int) Math.ceil(semiCansRequiredDecimal);
        int primerCansRequiredInt  = (int) Math.ceil(primerCansRequiredDecimal);
        double surfaceAreaInUnit = answerM;
        //setting up conversion into the users desired unit. Notice that "unit" is an integer. I tried to use enumeration instead but passing the index of what was selected in the menu was more convenient for me
        //using conversion factors to calculate values in new units. Also setting the unit symbol
        String unitSymbol = "m";
        switch(unit){
            case 0:
                surfaceAreaInUnit = answerM;
                unitSymbol = "m";
                break;
            case 1:
                surfaceAreaInUnit = answerCM;
                unitSymbol = "cm";
                break;
            case 2:
                surfaceAreaInUnit = answerM*SQUARE_FEET_IN_SQUARE_M;
                unitSymbol = "ft";
                break;
            case 3:
                surfaceAreaInUnit = answerM*SQUARE_INCH_IN_SQUARE_M;
                unitSymbol = "in";
                break;
        }


        //breaking up the output into different strings representing a different part of the message

        String sSurfaceArea = "Total surface area to be painted: "+ surfaceAreaInUnit+""+unitSymbol+"\u00B2\n";
        String sLitres = "Litres of paint required per coat: "+PaintLitresRequired+"L\n";
        String sIntro = "Your calculations for: \n length: "+roomLength+" width: "+roomWidth+" height: "+roomHeight+"\n";


        String sCans = "Cans of primed required: "+primerCansRequiredInt+"\nCans of semi-gloss required: "+semiCansRequiredInt;

        //using the rounded (floored) value if the rounding mode is set to integer




        //Using this to prevent the showing of an extra decimal if the value happens to be a whole number
        if(PaintLitresRequired == Math.floor(PaintLitresRequired)){
            int intPaintLitresRequired = (int)Math.floor(PaintLitresRequired);
            sLitres = "Litres of paint required per coat: "+intPaintLitresRequired+"L\n";

        }

        //Using this to prevent the showing of an extra decimal if the value happens to be a whole number
        if(surfaceAreaInUnit == Math.floor(surfaceAreaInUnit)){
            int roundedSurfaceArea = (int) Math.floor(surfaceAreaInUnit);
            sSurfaceArea = "Total surface area to be painted: "+ roundedSurfaceArea+""+unitSymbol+"\u00B2\n";

        }
        //No progress bar included in this project due to my goal of maximizing user interface and user experience

        //Show a message dialog with all the strings displaying
        JOptionPane.showMessageDialog(null, sIntro+sSurfaceArea+sLitres+sCans, "Calculations", JOptionPane.INFORMATION_MESSAGE);

    }

    //actionPerformed method that gets called everytime a certain action regarding a certain object is performed
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        //if the action was the pressing of the submit button
        if(actionEvent.getSource() == submitButton){
            double paintVolume;
            //if the user didn't select a custom volume use the default one
            if(paintVolumeSelector.getSelectedItem()== paintVolumeOptions[0]){
                paintVolume = defaultPaintVolume;
            }
            //if the user selected a custom volume use that
            else{
                paintVolume = Double.parseDouble((String) paintVolumeSelector.getSelectedItem());

            }
            //open the paint program with all the selected values from all the dropdown menus
            openPaintProgram(unitMenu.getSelectedIndex(), paintVolume, (int)primerSelector.getSelectedItem(),  (int)semiSelector.getSelectedItem(), (int)precisionSelector.getSelectedItem(), roundingModeSelector.getSelectedIndex());

        }

        //if the action that occurred was the changing of a value in the paint volume dropdown menu, and it was changed to custom
        if(actionEvent.getSource() == paintVolumeSelector && paintVolumeSelector.getSelectedItem() == "Custom"){

            //if the user wanted a custom value make an input box to get that value from them, and if they close the window, go back to the default
            double newVol = checkJOptionInput("Please select new paint volume per can",1,defaultPaintVolume);

            //if they selected the same value as the default, don't make a new entry just select default
            if(newVol == defaultPaintVolume){
                paintVolumeSelector.setSelectedItem(paintVolumeOptions[0]);
            }
            //otherwise, add the new value they chose to the dropdown menu and have it be selected
            else {
                paintVolumeSelector.addItem(String.valueOf(newVol));
                paintVolumeSelector.setSelectedItem(String.valueOf(newVol));
            }
        }

    }

}
