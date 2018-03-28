package mininet;

/**
 * The person class holds the basic data members that are common
 * for its child classes.
 * 
 * @author :Siva Kowshik Sripathi Panditharadyula
 */
public abstract class Person {
    public String Name;
    protected int Age;
    protected String Gender;
    protected String City;
    protected String Country;
    public String Status;
    public char Type;
    public String []Friends = new String[9999];
    public int FriendsCount;
}