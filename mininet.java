package mininet;

import java.util.Scanner;

/**
 * This class is designed to serve as the driver for the application.
 * It has the main() function and other necessary functions
 * that add, delete and make changes to the profiles.
 * 
 * @author :Siva Kowshik Sripathi Panditharadyula
 * @version 1.0
 */
public class mininet {
    static Adult[] adults = new Adult[9999];
    static Dependant[] dependants = new Dependant[9999];
    static int adultCount=0;
    static int dependantCount=0;
    static String friendTemp;
    
    /**
     * Display the main menu
     */
    public static void Menu(){
        System.out.println("\n=================\nMiniNet Main Menu\n=================");
        System.out.println("Press\tFunctionality\n---------------------------------");
        System.out.println("1:\tAdd new person to network\n2:\tSelect a person by name\n3:\tList all persons in network\n0:\tExit\n");
    }
    
    /**
     * Add new person to the network
     */
    public static void Add(){
        Scanner sc = new Scanner(System.in);
        String name,status,gender,city,country;
        String parent1,parent2;
        char type; //'a' represents adult, 'd' represents dependant
        int age,choice=1,pos;
        int p1,p2;
        String temp[];
        
        System.out.println("\nEnter name of the person: ");
        name = sc.nextLine();
        System.out.println("Enter age of the person: ");
        age = sc.nextInt();
        sc.nextLine();
        while(true){
            System.out.println("Enter gender of the person(Male/Female): ");
            gender = sc.nextLine();
            if(gender.equalsIgnoreCase("male")||gender.equalsIgnoreCase("female"))
                break;
            else
                System.out.println("Please enter Male or Female!!");
        }
        System.out.println("Enter city: ");
        city = sc.nextLine();
        System.out.println("Enter country: ");
        country = sc.nextLine();
        System.out.println("Enter initial Status of the person: ");
        status = sc.nextLine();
        
        //check if adult or dependant
        if(age<16){ //for dependants
            type='d';
            while(true){
                System.out.println("Enter name of parent1: ");
                parent1 = sc.nextLine();
                temp=Search(parent1).split(" ");
                if(temp[0]=="d"){
                    System.out.println(parent1+" is not an adult.");
                    continue;
                }
                else if(temp[0].equals("x")){
                    System.out.println("\nNo account exists by the name "+parent1+".");
                    continue;
                }
                else{
                    p1=Integer.parseInt(temp[1]);
                    break;
                }
            }
            while(true){
                System.out.println("Enter name of parent2: ");
                parent2 = sc.nextLine();
                temp=Search(parent2).split(" ");
                if(temp[0]=="d"){
                    System.out.println(parent2+" is not an adult.");
                    continue;
                }
                else if(temp[0].equals("x")){
                    System.out.println("\nNo account exists by the name "+parent2+".");
                    continue;
                }
                else{
                    p2=Integer.parseInt(temp[1]);
                    break;
                }
            }     
            dependants[dependantCount++]=new Dependant(name,age,gender,city,country,type,status,parent1,parent2);
            adults[p1].addChild(name);
            adults[p2].addChild(name);
        }
        else{ //for adults
            type='a';
            adults[adultCount++]=new Adult(name,age,gender,city,country,type,status);
        }
        System.out.println("\nProfile Created");
    }
    
    /**
    * Search for a person in the adults and dependants section
    */
    public static String Search(String searchName){
        for(int i=0;i<adultCount;i++){
            if(adults[i].Name.equalsIgnoreCase(searchName))
                return "a "+i; //return type+SPACE+position
        }
        for(int i=0;i<dependantCount;i++){
            if(dependants[i].Name.equalsIgnoreCase(searchName))
                return "d "+i; //return type+SPACE+position
        }
        return "x x"; //return failure
    }
    
    /**
    * display profile menu
    */
    public static void Profile(String type, int pos){
        Scanner sc = new Scanner(System.in);
        int choice=1;
        String newCity="", newCountry="",newStatus="";
        String fName;
        
        while(choice!=0){
            if(type.equals("a")){
                System.out.println("\n-----------------------\nProfile Menu for "+adults[pos].Name+"\n-----------------------");
            }
            else{
                System.out.println("\n-----------------------\nProfile Menu for "+dependants[pos].Name+"\n-----------------------");
            }
            System.out.print("\nPress 1: Display profile\nPress 2: Update profile\nPress 3: Delete profile\nPress 4: Add friends\nPress 5: Check friendship\nPress 6: List all friends\nPress 0: Back to main menu\nEnter choice: ");
            choice=sc.nextInt();
            sc.nextLine();
            switch(choice){
                 case 1: //DISPLAY
                    if(type.equals("a")){
                        adults[pos].display();
                    }
                    else{
                        dependants[pos].display();
                    }
                    break;
                 case 2: //UPDATE
                    System.out.println("Press 1 to change city, press any other key to cancel");
                    if(sc.nextInt()==1){
                        sc.nextLine();
                        System.out.println("Enter new city name: ");
                        newCity=sc.nextLine();
                    }
                    System.out.println("Press 1 to change country, press any other key to cancel");
                    if(sc.nextInt()==1){
                        sc.nextLine();
                        System.out.println("Enter new country name: ");
                        newCountry=sc.nextLine();
                    }
                    System.out.println("Press 1 to change status, press any other key to cancel");
                    if(sc.nextInt()==1){
                        sc.nextLine();
                        System.out.println("Enter new status: ");
                        newStatus=sc.nextLine();
                    }
                    if(type.equals("a")){
                        adults[pos].update(newCity,newCountry,newStatus);
                    }
                    else{
                        dependants[pos].update(newCity,newCountry,newStatus);
                    }
                    break;
                 case 3://DELETE
                    System.out.print("Are you sure you would like to delete this account?\nPress 1: Yes\nPress any other key: No\nEnter...");
                    if(sc.nextInt()==1){
                        sc.nextLine();
                        if(type.equals("a")){
                            if(adults[pos].getChildrenCount()==0)
                                System.out.print("\nThis person has got one or more children. Therefore, profile cannot be deleted");
                            else
                                DeletePro(type,pos);
                        }
                        else{
                            DeletePro(type,pos);
                        }
                        choice=0; //force back to main menu
                    }
                    break;
                 case 4: //ADD FRIENDS
                     int fPos=addFriends(type);
                     if(fPos!=-1){
                        if(type.equals("a")){
                            adults[pos].addAFriend(friendTemp);
                            adults[fPos].addAFriend(adults[pos].Name);
                            System.out.println("\n"+adults[pos].Name+" and "+friendTemp+" are now friends!!");
                        }
                        else{
                            if(dependants[pos].checkAge()==0)
                                System.out.println("\n"+dependants[pos].Name+" cannot have friends at the age of 2");
                            else if(dependants[fPos].checkAge()==0)
                                System.out.println("\n"+dependants[fPos].Name+" cannot have friends at the age of 2");
                            else{
                                if(dependants[pos].checkDiff(dependants[fPos])==1){
                                    dependants[pos].addAFriend(friendTemp);
                                    dependants[fPos].addAFriend(dependants[pos].Name);
                                    System.out.println("\n"+dependants[pos].Name+" and "+friendTemp+" are now friends!!");
                                }
                                else{
                                    System.out.println("\n"+dependants[pos].Name+" and "+friendTemp+" cannot be friends due to age barriers!! Their age gap is more than 3!!");
                                }
                            }
                            
                        }
                     }
                    break;
                 case 5: //check friendship
                    System.out.println("Enter other person's name to check friendship: ");
                    fName = sc.nextLine();
                    if(type.equals("a"))
                        adults[pos].checkFriendship(fName);
                    else
                        dependants[pos].checkFriendship(fName);
                    break;
                 case 6: //list all friends
                     if(type.equals("a"))
                        adults[pos].listFriends();
                    else
                        dependants[pos].listFriends();
                    break;
                 case 0: //BACK to main menu
                    break;
                 default:
                     System.out.println("Wrong choice!! Enter again.");
            }
        }
    }
    
    /**
    * display all profiles
    */
    public static void List(){
        int i;
        
        System.out.println("\n-----------------------------------------\nThe profiles of the Adults are as follows:\n-----------------------------------------");
        if(adultCount==0)
            System.out.println("\nNo accounts found!!");
        else{
            for(i=0;i<adultCount;i++){
                System.out.println("\nProfile "+(i+1));
                adults[i].display();
                System.out.println("--------------------------------------");
            }
        }
        
        System.out.println("\n-----------------------------------------\nThe profiles of Dependants are as follows:\n-----------------------------------------");
        if(adultCount==0)
            System.out.println("No accounts found!!");
        else{
            for(i=0;i<dependantCount;i++){
                System.out.println("Profile "+(i+1));
                dependants[i].display();
                System.out.println("--------------------------------------");
            }
        }
    }
    
    /**
    * Add new friends
    */
    public static int addFriends(String type){
        Scanner sc = new Scanner(System.in);
        String name,searchCheck;
        int pos;
        System.out.println("Enter friend's name: ");
        name = sc.nextLine();
        searchCheck=Search(name);
        String temp[] = searchCheck.split(" ");
        if(temp[1].equals("x")){
            System.out.println("\nPerson not found!!");
            return -1;
        }
        else{
            pos=Integer.parseInt(temp[1]);
            if(!type.equals(temp[0])){
                System.out.println("\nSorry!! This person cannot be friends with "+name+" due to age barriers.");
                return -1;
            }
            else{
                friendTemp=name;
                return pos;
            }
        }
    }
    
    /**
     * Delete profile
     */
    public static void DeletePro(String type,int pos){
        int range;
        if(type.equals("a")){
            if(adultCount!=1)
                range=adultCount;
            else
                range=adultCount-1;
            for(int i=pos;i<range;i++){
                adults[i]=adults[i+1];    
            }
            System.out.println("\nProfile Deleted!!"+adultCount);
            adultCount--;
        }
        else{
            if(dependantCount!=1)
                range=dependantCount;
            else
                range=dependantCount-1;
            for(int i=pos;i<range;i++){
                dependants[i]=dependants[i+1];         
            }
            System.out.println("\nProfile Deleted!!"+dependantCount);
            dependantCount--;
        }
    }
    /**
     * Initiate the program with dummy hard-coded data
     */
    public static void initiate(){
        
        adults[adultCount++]=new Adult("Garry Linker",38,"Male","NY","USA",'A',"Working at KFC");
        adults[adultCount++]=new Adult("Mona Jones",33,"Female","NY","USA",'A',"Artist at Freelance Business");
        adults[adultCount++]=new Adult("Robert Ponting",23,"Male","Melbourne","Australia",'A',"Student at MIT");
        adults[adultCount++]=new Adult("Rachel Greene",31,"Female","Tokyo","Japan",'A',"Buyer at Bloomingdales");
        adults[adultCount++]=new Adult("Ross Geller",32,"Male","London","UK",'A',"Paleantologist at Oxford");
        adults[adultCount++]=new Adult("Joey Tribiani",30,"Male","Milan","Italy",'A',"Actor");
        dependants[dependantCount++]=new Dependant("Billy",14,"Male","NY","USA",'D',"Studying at Grads' High School","Garry Linker","Mona Jones");
        adults[0].addChild("Billy");
        adults[1].addChild("Billy");
        dependants[dependantCount++]=new Dependant("Ema",2,"Female","Tokyo","Japan",'D',"Yet to go to KG","Ross Geller","Rachel Greene");
        adults[3].addChild("Ema");
        adults[4].addChild("Ema");
        dependants[dependantCount++]=new Dependant("Eliot",12,"Male","LA","USA",'D',"At School","Garry Linker","Mona Jones");
        adults[0].addChild("Eliot");
        adults[1].addChild("Eliot");
        
        adults[0].addAFriend("Robert Ponting");
        adults[2].addAFriend("Garry Linker");
        
        adults[0].addAFriend("Joey Tribiani");
        adults[5].addAFriend("Garry Linker");
        
        adults[1].addAFriend("Joey Tribiani");
        adults[5].addAFriend("Mona Jones");
        
        adults[1].addAFriend("Ross Geller");
        adults[4].addAFriend("Mona Jones");
        
        dependants[0].addAFriend("Eliot");
        dependants[2].addAFriend("Billy");
        
        System.out.println("\nInitial data loaded...");
    }
    /**
    * main function or the driver method for the program
    * The main menu is hosted and entries are controlled from here
    */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice=1, i, j, pos;
        String searchCheck;
        String searchName;
        
        System.out.println("\nWelcome to MiniNet\n------------------");
        initiate();
        while(choice!=0){
            Menu();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1: 
                    Add();
                    break;
                case 2: 
                    System.out.println("Enter name to search: ");
                    searchName = sc.nextLine();
                    searchCheck=Search(searchName);
                    String temp[] = searchCheck.split(" ");
                    
                    if(temp[1].equals("x")){
                        System.out.println("\nNot found!!");
                    }
                    else{
                        pos=Integer.parseInt(temp[1]);
                        String type = temp[0];
                        Profile(type,pos);
                    }
                    break;
                case 3: 
                    List(); //to display all accounts
                    break;
                case 0:
                    System.out.println("\nNetwork closing down... Bid farewell to all friends and family...\nThank you for using MiniNet!!");
                    break;
                default:
                    System.out.println("Wrong choice!! Try again");
                    
            }
        }
        
    }
    
}