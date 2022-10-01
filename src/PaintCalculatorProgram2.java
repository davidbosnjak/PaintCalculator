
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.*;


public class PaintCalculatorProgram2 implements ActionListener {
    public static JButton submitButton = new JButton("Start Calculation");
    static String[] unitChoices = {"Meters","Centimeters","Feet","Inches"};
    static Integer[] paintChoices = {1,2,3,4,5};

    static JComboBox<String> unitMenu = new JComboBox<String>(unitChoices);

    static JComboBox<String> paintVolumeSelector = new JComboBox<String>(new String[]{"Default", "Custom"});

    static JComboBox<Integer> primerSelector = new JComboBox<Integer>(paintChoices);
    static JComboBox<Integer> semiSelector = new JComboBox<Integer>(paintChoices);
    static ProgressMonitor progressBar = new ProgressMonitor(null, "Calculating paint needed", "note", 0,100);
    static JPanel panel = new JPanel();


    public static void main(String[] args) throws InterruptedException{

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Paint Calculator");
        frame.setSize(400,400);

        frame.add(panel);

        panel.setLayout(null);

        JLabel titleMessage = new JLabel("Paint Calculator Main Menu");
        titleMessage.setFont(new Font("Times", Font.BOLD,20));
        titleMessage.setBounds(50,20,400,25);
        panel.add(titleMessage);

        JLabel unitMessage = new JLabel("Select Unit:");
        unitMessage.setBounds(30,60,150,20);
        panel.add(unitMessage);


        ;
        unitMenu.addActionListener(new PaintCalculatorProgram2());
        unitMenu.setBounds(30,80,150,25);
        panel.add(unitMenu);

        submitButton.addActionListener(new PaintCalculatorProgram2());
        submitButton.setBounds(200,300,150,30);
        panel.add(submitButton);
        frame.setVisible(true);

        JLabel paintVolumeMessage = new JLabel("Select can volume:");
        paintVolumeMessage.setBounds(30,110,150,20);
        panel.add(paintVolumeMessage);

        paintVolumeSelector.setBounds(30,130,150,20);
        paintVolumeSelector.addActionListener(new PaintCalculatorProgram2());
        panel.add(paintVolumeSelector);

        JLabel primerCoats = new JLabel("Select # of primer coats");
        primerCoats.setBounds(30,160,150,20);
        panel.add(primerCoats);

        primerSelector.setBounds(30,180,150,20);
        primerSelector.addActionListener(new PaintCalculatorProgram2());
        Integer defaultPrimer = 2;

        panel.add(primerSelector);

        JLabel semiCoats = new JLabel("Select # of semi coats");
        semiCoats.setBounds(30,210,150,20);
        panel.add(semiCoats);

        semiSelector.setBounds(30,230,150,20);
        semiSelector.addActionListener(new PaintCalculatorProgram2());
        panel.add(semiSelector);
        semiSelector.setSelectedItem(2);



    }
    //method to get the surface area of the walls based on dimensions of the room
    public static double getWallArea(int length, int width, int height){
        return length*height*2+ width*height*2;
    }
    //method used for error checking inputs in JOptionPane dialog boxes. Use message parameter to set a message for input
    public static double checkJOptionInput(String message, int mode){

        double parsedNumber;
        //infinite loop until an appropriate value is inputted by the user
        while(true){


            String userInput = JOptionPane.showInputDialog(null, message);

            //if a short can successfully be parsed from the input string without error, it will break out of the loop. Otherwise, it will keep asking for a correct input and showing an error message
            try{
                if(mode ==0) {
                    parsedNumber = Short.parseShort(userInput);
                }
                else{
                   parsedNumber = Double.parseDouble(userInput);
                }
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
        int factor = (int) Math.pow(10,digitsToShow-1);
        int count = 1;
        while(numToRound<100){
            numToRound *=factor;
            count++;
        }
        numToRound = Math.floor(numToRound);
        for(int i =1; i<count; i++){
            numToRound/=factor;
        }

        return numToRound;
    }
    public static void openPaintProgram(int unit, double canVolume, int primerPerWall, int semiPerWall) throws InterruptedException{
        //declaring constants instead of hardcoding them
        final short SQUARE_CM_IN_SQUARE_M = 10000;
        final double SQUARE_FEET_IN_SQUARE_M = 10.7639;
        final short SQUARE_INCH_IN_SQUARE_M = 1550;

        final short METERS_PER_PAINT = 11;
        final double PAINT_IN_CAN = canVolume;
        final int SEMIGLOSS_PER_WALL = semiPerWall;
        final int PRIMER_PER_WALL = primerPerWall;

        //JProgressMonitor object being initialized
        //I did not make a JOptionPane object as most of its methods are static and no object is required

        //parsing the strings from the input windows into shorts
        short roomLength = (short) checkJOptionInput("Please enter the length of your room in CM",0);
        short roomWidth = (short) checkJOptionInput("Please enter the width of your room in CM",0);
        short roomHeight = (short) checkJOptionInput("Please enter the height of your room in CM",0);

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
        double surfaceAreaInUnit = answerM;
        String unitSymbol = "M";
        switch(unit){
            case 0:
                surfaceAreaInUnit = answerM;
                unitSymbol = "M";
                break;
            case 1:
                surfaceAreaInUnit = answerCM;
                unitSymbol = "CM";
                break;
            case 2:
                surfaceAreaInUnit = answerM*SQUARE_FEET_IN_SQUARE_M;
                unitSymbol = "FT";
                break;
            case 3:
                surfaceAreaInUnit = answerM*SQUARE_INCH_IN_SQUARE_M;
                unitSymbol = "IN";
                break;
        }

        //breaking up the output into different strings representing a different part of the message
        String sIntro = "Your calculations for: \n length: "+roomLength+" width: "+roomWidth+" height: "+roomHeight+"\n";
        String sSurfaceArea = "Total surface area to be painted: "+ surfaceAreaInUnit+""+unitSymbol+"\u00B2\n";
        String sLitres = "Litres of paint required per coat: "+dPaintLitresRequired+"L\n";
        String sCans = "Cans of primed required: "+primerCansRequiredInt+"\nCans of semi-gloss required: "+semiCansRequiredInt;

        //using a for loop going from 0 to 100 to set the progress on my progress bar. using a delay of 100ms for each percentage completion.
        /*for(int i =0; i<=50; i++){
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0L;
            while(elapsedTime<50){
                elapsedTime = (new Date()).getTime()-startTime;
            }
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
        }*/

        //showing a message containing all the strings after the progress bar has completed
        JOptionPane.showMessageDialog(null, sIntro+sSurfaceArea+sLitres+sCans, "Calculations", JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == submitButton){
            double paintVolume;
            if(paintVolumeSelector.getSelectedItem()== "Default"){
                paintVolume = 3.78;
            }
            else{
                paintVolume = Double.parseDouble((String) paintVolumeSelector.getSelectedItem());

            }
            System.out.println("paint can volume is "+paintVolume);
            try {

                openPaintProgram(unitMenu.getSelectedIndex(), paintVolume, (int)primerSelector.getSelectedItem(),  (int)semiSelector.getSelectedItem());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(actionEvent.getSource() == (paintVolumeSelector) && paintVolumeSelector.getSelectedItem() == "Custom"){
            double newVol = checkJOptionInput("Please select new paint volume per can",1);
            paintVolumeSelector.addItem(String.valueOf(newVol));
            paintVolumeSelector.setSelectedItem(String.valueOf(newVol));
        }
        if(actionEvent.getSource() == unitMenu){
            System.out.println("something happened in the menu");
            String selected = (String) unitMenu.getSelectedItem();
            System.out.println(selected);
        }
    }

}
