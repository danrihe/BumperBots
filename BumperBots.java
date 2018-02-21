/*
 * WELCOME TO BUMPERBOTS
 * DANRI 12A
 * ICS4U
 * MR. V
 * DECEMBER 7TH 2015 2:30 AM
 */
import becker.robots.*;
//import static becker.robots.City;
import java.util.*;
import java.awt.Color;
public class BumperBots
{
    public static Robot[] bumperBot = new Robot[100];  //create a global boolean array with value of 100 (excessive)
    public static Boolean[] bumperBotCrash = new Boolean[100]; //create a global boolean array of size 100 (excessive)
    public static int[] bumperBotLives = new int[100]; //create a bumperBotInteger array with length equal to number of players (excessive)
    public static int botsLeft, numOfPlayers;
    public static Boolean gameOver = false;   //create a variable of type boolean and set it to false
    public static void main(String args[])
    {
        System.out.println("Welcome to BumperBots! The goal of the game is to the last robot standing! \nWhen you hit another robot, you bounce off in another direction! \nEach robot can only get hit five times. If you are hit more than five times, you die. \nYou only lose lives when you are hit by another bot. If you hit another bot, only the bot you hit loses lives. \nOnce a bot runs out of lives, it becomes immobilized (and the color changes to blue) \nAlthough it can no longer move, 'dead' bots can still be hit by bots that are still alive, so watch out! /nHave fun, and every bot for themselves!");
        Scanner in = new Scanner(System.in); //create a scanner object
        City arena = new City(10,15);   //build and identify a 'city' for robots to move in

        Wall[] arenaLimits = new Wall[54]; //create an array of walls

        Random numGen = new Random();   //create a random number generator

        int direction, whichBot,moveMagnitude; //create the following variables
        int userMoveMagnitude = 0;
        //create the following variables and initialize their value to 0
        int southWalls = 1;
        int northWalls = 1;
        int eastWalls = 1;
        //String botColor = "";

        String userDirection;

        System.out.println("How many players would you like? (The game is optimized for between 2-10 players)");         //ask the user for input
        numOfPlayers = (Integer.parseInt(in.nextLine()));   //record user input as variable

        //Boolean[] bumperBotCrash = Boolean[numOfPlayers];

        botsLeft = numOfPlayers;
        //Robot[] bumperBot = new Robot[numOfPlayers];   //make an array of robots according to user input

        Arrays.fill(bumperBotCrash, Boolean.FALSE); //fill bumperBotCrash boolean array with false

        Arrays.fill(bumperBotLives, 5); //fill array bumperBotLives with value of 1

        //the following for loops create walls to surround the arena
        for (int i = 1; i < 9; i ++)
        {
            arenaLimits[i] = new Wall(arena, i, 0, Direction.EAST);
        }
        for (int i = 9; i < 22; i ++)
        {
            arenaLimits[i] = new Wall(arena, 9, southWalls, Direction.NORTH);
            southWalls += 1;
        }
        for (int i = 22; i <35; i ++)
        {
            arenaLimits[i] = new Wall(arena, 0, northWalls, Direction.SOUTH);
            northWalls += 1;
        }
        for (int i = 35; i < 43; i ++)
        {
            arenaLimits[i] = new Wall(arena, eastWalls, 14, Direction.WEST);
            eastWalls += 1;
        }

        //assign a coordinate for each bumperBot
        for (int i = 0; i < numOfPlayers; i++)
        {
            bumperBot[i] = new Robot(arena, (numGen.nextInt(8)+1), (numGen.nextInt(13)+1), Direction.EAST, 1);
        }

        //assign a label to each respective robot
        for (int i = 1; i < numOfPlayers; i++)
        {
            bumperBot[i].setLabel("Player " + i);
        }

        System.out.println("You are the yellow bumper car.");   //display message to user
        bumperBot[0].setColor(Color.YELLOW);    //set the color of first bumperBot to yellow

        do
        {
            whichBot = numGen.nextInt(numOfPlayers-1)+1;

            if (bumperBotCrash[0] == false)
            {
                System.out.println("Would you like to go right (r), left (l), backwards (b), or straight (s)?");
                userDirection = in.nextLine();
                if (userDirection.equals("r"))  //if user input is equal to r
                {
                    for (int i = 0; i < 3; i++) //turn robot left three times
                    {
                        bumperBot[0].turnLeft();
                    }
                }
                else if (userDirection.equals("l")) //if user input is equal to l
                {
                    bumperBot[0].turnLeft();    //turn robot left once
                }
                else if (userDirection.equals("b")) //if user input is equal to b
                {
                    for (int i = 0; i < 2; i++) //turn robot left twice
                    {
                        bumperBot[0].turnLeft();
                    }
                }
                do{ //run the following loop while user input is greater than 4
                    System.out.println("How many blocks would you like to move? (Maximum 4)");  //ask for user input
                    userMoveMagnitude = Integer.parseInt(in.nextLine());
                }while(userMoveMagnitude > 4);

                //repeat the following loop according to user input
                for (int m = 0; m < userMoveMagnitude; m++)
                {
                    //if there is nothing in front of the robot, run the following loop
                    if (bumperBot[0].frontIsClear())
                    {
                        bumperBot[0].move();    //move the user's robot forward

                        checkCollision(0);
                    }
                }
            }

            //run the following loop according to how many players
            for (int i = 1; i < numOfPlayers; i++)
            {
                direction = numGen.nextInt(4);  //generate a random number between 0 and 3 for direction
                moveMagnitude = numGen.nextInt(5)+1;    //generate a random number between 1 and 5 for moveMagnitude
                if (bumperBotCrash[i] == false)  //if bumperBotCrash for the specified robot is false, run the loop
                {
                    switch(direction)   //according to direction, compare the following cases
                    {
                        case 2: //direction == 2; turn bumperBot once left
                        bumperBot[i].turnLeft(); 
                        break;
                        case 3: //direction == 3; turn bumperBot left twice
                        for (int x = 0; x < 2; x++)
                        {
                            bumperBot[i].turnLeft();
                        }
                        break;
                        case 4: //direction == 4; turn bumperBot left three times
                        for (int x = 0; x < 3; x++)
                        {
                            bumperBot[i].turnLeft();
                        }
                        break;
                        default:    //anything else, break
                        break;
                    }

                    //run the following loop according to the moveMagnitude generated
                    for (int d = 0; d < moveMagnitude; d++)
                    {
                        //if there is nothing in front of the specified bumperBot
                        if (bumperBot[i].frontIsClear())
                        {
                            bumperBot[i].move();    //move the bumperBot forward once

                            checkCollision(i);
                        }
                    }
                }
            }
        }while(gameOver == false);
    }

    public static void checkCollision(int i)
    {
        for (int x = 0; x < numOfPlayers; x++)
        {
            if (x != i) //if x is not equal to i
            {
                //if there is more than one robot on the specified intersection, run the following conditions
                if ((bumperBot[x].getIntersection().countSims(IPredicate.anyRobot)) != 1 )
                {
                    bumperBotLives[x] -= 1; //decrease specified bumperBotLives by 1

                    //if bumperBotLives is greater than 0, display message to user
                    if (bumperBotLives[x] > 0 && x > 0)
                    {
                        System.out.println("BumperBot " + x + " has been hit! He has " + bumperBotLives[x] + " lives remaining.");
                    }
                    else if (bumperBotLives[x] > 0 && x == 0)
                    {
                        System.out.println("You have been hit! You have " + bumperBotLives[x] + " lives remaining.");
                    }

                    //if bumperBotLives is equal to 0
                    if (bumperBotLives[x] == 0)
                    {
                        if (x > 0)
                        {
                            bumperBot[x].setLabel("WASTED");    //set label of specified robot
                            bumperBot[x].setColor(Color.BLUE);
                            System.out.println("BumperBot " + x + " has been WASTED!"); //display message
                            bumperBotCrash[x] = true;   //set specified boolean to true
                            botsLeft -= 1;  //decreases botsLeft by one
                        }
                        else
                        {
                            bumperBot[x].setLabel("WASTED");    //set lavel of specified robot
                            System.out.println("You have been WASTED!");
                            bumperBotCrash[x] = true;
                            botsLeft -= 1;
                        }
                    }
                    if (bumperBot[x].getDirection() == bumperBot[i].getDirection()) //if directions of bumperBots associated with i and x are equal
                    {
                        for (int a = 0; a < 2; a++) //turn robot i around (twice)
                        {
                            bumperBot[i].turnLeft();
                        }
                    }
                    else    //if directions of bumperBots associated with i and x are not equal
                    {
                        for (int a = 0; a < 2; a++) //turn each robot around (twice)
                        {
                            bumperBot[x].turnLeft();
                            bumperBot[i].turnLeft();
                        }
                    }

                    if (bumperBot[x].frontIsClear() == false)   //if there is something blocking the way of a robot
                    {
                        for (int t = 0; t < 2; t++) //turn the robot around (twice)
                        {
                            bumperBot[x].turnLeft();
                        }
                    }
                    if (bumperBot[0].frontIsClear() == false)   //if there is something blocking the user robot
                    {
                        for (int t = 0; t < 2; t++) //turn the robot around (twice)
                        {
                            bumperBot[0].turnLeft();
                        }
                    }

                    bumperBot[x].move();    //move robot x forward one
                    chainCollisions(x, i);  //call chainCollisions method
                    bumperBot[i].move();    //move robot i forward one
                    chainCollisions(x, i);  //call chainCollisions method
                }
                if (botsLeft == 1)  //if there is one robot left
                {
                    gameOver = true;    //set gameOver to true, effectively breaking the loop
                    //run the following loop according to number of players
                    for (int y = 0; y < numOfPlayers; y++)
                    {
                        if (bumperBotCrash[y] == false) //check which bumperBot survived 
                        {
                            if (y != 0) //if the computer wins, display following message
                            {
                                System.out.println("BumperBot " + y + " wins!");
                            }
                            else if (y == 0)
                            {
                                System.out.println("Congratulations, you win!");   
                            }
                        }
                    }
                }
            }   
        }
    }
    //method to determine whether or not there is a 'chain' reaction collision occurring.
    public static void chainCollisions(int x, int i)
    {
        if ((bumperBot[x].getIntersection().countSims(IPredicate.anyRobot)) != 1 )
        {
            bumperBotLives[x] -= 1; //decrease specified bumperBotLives by 1

            //if bumperBotLives is greater than 0, display message to user
            if (bumperBotLives[x] > 0 && x > 0)
            {
                System.out.println("BumperBot " + x + " has been hit! He has " + bumperBotLives[x] + " lives remaining.");
            }
            else if (bumperBotLives[x] > 0 && x == 0)
            {
                System.out.println("You have been hit! You have " + bumperBotLives[x] + " lives remaining.");
            }

            //if bumperBotLives is equal to 0
            if (bumperBotLives[x] == 0)
            {
                if (x > 0)
                {
                    bumperBot[x].setLabel("WASTED");    //set label of specified robot
                    bumperBot[x].setColor(Color.BLUE);
                    System.out.println("BumperBot " + x + " has been WASTED!"); //display message
                    bumperBotCrash[x] = true;   //set specified boolean to true
                    botsLeft -= 1;  //decreases botsLeft by one
                }
                else
                {
                    bumperBot[x].setLabel("WASTED");    //set lavel of specified robot
                    System.out.println("You have been WASTED!");
                    bumperBotCrash[x] = true;
                    botsLeft -= 1;
                }
            }
            if (bumperBot[x].getDirection() == bumperBot[i].getDirection()) //if directions of bumperBots associated with i and x are equal
            {
                for (int a = 0; a < 2; a++) //turn robot i around (twice)
                {
                    bumperBot[i].turnLeft();
                }
            }
            else    //if directions of bumperBots associated with i and x are not equal
            {
                for (int a = 0; a < 2; a++) //turn each robot around (twice)
                {
                    bumperBot[x].turnLeft();
                    bumperBot[i].turnLeft();
                }
            }

            if (bumperBot[x].frontIsClear() == false)   //if there is something blocking the way of a robot
            {
                for (int t = 0; t < 2; t++) //turn the robot around (twice)
                {
                    bumperBot[x].turnLeft();
                }
            }
            if (bumperBot[0].frontIsClear() == false)   //if there is something blocking the user robot
            {
                for (int t = 0; t < 2; t++) //turn the robot around (twice)
                {
                    bumperBot[0].turnLeft();
                }
            }
        }
    }
}
