/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicImp;

import dao.PersonAccess;
import dto.Person;
import entity.PersonEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import logic.PersonLogic;

/**
 *
 * @author apple
 */
@Stateless

public class PersonLogicImp implements PersonLogic {
    
    @EJB
    private PersonAccess pa;

    @Resource
    private EJBContext ejbContext;
    
    @Override
    public List<Person> getPersonList() {
        //throw new UnsupportedOperationException("Not supported yet."); 
//To change body of generated methods, choose Tools | Templates.
        List<PersonEntity> persons=pa.getPersonList();
        
        List<Person> result = new ArrayList<>(persons.size());
        for (PersonEntity pe : persons) {
            Person to = new Person();
            to.setUuid(pe.getUuid());
            to.setFirstName(pe.getFirstName());
            to.setLastName(pe.getLastName());
            result.add(to);
        }
        return result;
    }


    public PersonEntity createPerson(Person p) {
        PersonEntity pe=PersonEntity.newInstance();
        pe.setFirstName(p.getFirstName());
        pe.setLastName(p.getLastName());
        pe.setEmailAddress(p.getEmailAddress());
       
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public PersonEntity createPerson(PersonEntity p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createTestData() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         //if(pa.getPersonCount()>0) {
         //   return;
        //}
                final String[] FIRSTNAMES = {
            "Jacob", "Michael", "Joshua", "Matthew", "Christopher", "Andrew", "Daniel",
            "Ethan", "Joseph", "William", "Anthony", "Nicholas", "David", "Alexander",
            "Ryan", "Tyler", "James", "John", "Jonathan", "Brandon", "Christian",
            "Dylan", "Zachary", "Noah", "Samuel", "Benjamin", "Nathan", "Logan",
            "Justin", "Jose", "Gabriel", "Austin", "Kevin", "Caleb", "Robert", "Elijah",
            "Thomas", "Jordan", "Cameron", "Hunter", "Jack", "Angel", "Isaiah",
            "Jackson", "Evan", "Luke", "Jason", "Isaac", "Mason", "Aaron", "Connor",
            "Gavin", "Kyle", "Jayden", "Aidan", "Juan", "Luis", "Charles", "Aiden",
            "Adam", "Brian", "Eric", "Lucas", "Sean", "Nathaniel", "Alex", "Adrian",
            "Carlos", "Bryan", "Ian", "Jesus", "Owen", "Julian", "Cole", "Landon",
            "Diego", "Steven", "Chase", "Timothy", "Jeremiah", "Sebastian", "Xavier",
            "Devin", "Cody", "Seth", "Hayden", "Blake", "Richard", "Carter", "Wyatt",
            "Dominic", "Antonio", "Jaden", "Miguel", "Brayden", "Patrick", "Alejandro",
            "Carson", "Jesse", "Tristan", "Emily", "Madison", "Emma", "Hannah",
            "Olivia", "Abigail", "Isabella", "Ashley", "Samantha", "Elizabeth",
            "Alexis", "Sarah", "Alyssa", "Grace", "Sophia", "Taylor", "Brianna",
            "Lauren", "Ava", "Kayla", "Jessica", "Natalie", "Chloe", "Anna", "Victoria",
            "Hailey", "Mia", "Sydney", "Jasmine", "Morgan", "Julia", "Destiny",
            "Rachel", "Megan", "Kaitlyn", "Katherine", "Jennifer", "Savannah", "Ella",
            "Alexandra", "Haley", "Allison", "Maria", "Nicole", "Mackenzie", "Brooke",
            "Makayla", "Kaylee", "Lily", "Stephanie", "Andrea", "Faith", "Amanda",
            "Katelyn", "Kimberly", "Madeline", "Gabrielle", "Zoe", "Trinity", "Alexa",
            "Mary", "Jenna", "Lillian", "Paige", "Kylie", "Gabriella", "Rebecca",
            "Jordan", "Sara", "Addison", "Michelle", "Riley", "Vanessa", "Angelina",
            "Leah", "Caroline", "Sofia", "Audrey", "Maya", "Avery", "Evelyn", "Autumn",
            "Amber", "Ariana", "Jocelyn", "Claire", "Jada", "Danielle", "Bailey",
            "Isabel", "Arianna", "Sierra", "Mariah", "Aaliyah", "Melanie", "Erin",
            "Nevaeh", "Brooklyn", "Marissa", "Jacqueline"
        };
                final String[] LASTNAMES = {
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller",
            "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White",
            "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", "Clark",
            "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young",
            "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", "Adams",
            "Baker", "Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Roberts",
            "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins",
            "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Morgan", "Bell",
            "Murphy", "Bailey", "Rivera", "Cooper", "Richardson", "Cox", "Howard",
            "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James", "Watson",
            "Brooks", "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross",
            "Henderson", "Coleman", "Jenkins", "Perry", "Powell", "Long", "Patterson",
            "Hughes", "Flores", "Washington", "Butler", "Simmons", "Foster", "Gonzales",
            "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Hayes"
        };
        Random rnd = new Random();

        List<PersonEntity> persons = new ArrayList<>();
        HashMap<String, Integer> names = new HashMap<>();
        for (int i = 0; i < 150; i++) {
            String firstName = FIRSTNAMES[rnd.nextInt(FIRSTNAMES.length)];
            String lastName = LASTNAMES[rnd.nextInt(LASTNAMES.length)];
            String name = firstName.toLowerCase().substring(0, 1) + lastName.toLowerCase();
            
            // assert that no duplicate names occur
            if (names.containsKey(name)) {
                int n = names.get(name) + 1;
                names.put(name, n);
                name = name + n;
            } else {
                names.put(name, 1);
            }   
            PersonEntity pe = pa.createPerson(name + "@uni-koblenz.de");
            pe.setFirstName(firstName);
            pe.setLastName(lastName);
            pe.setEmailAddress(name+"@uni-koblenz.de");
            persons.add(pe);
        }
        

       // List<PersonEntity> persons = new ArrayList<>();
        //PersonEntity pe = pa.createPerson("csun" + "@acme.edu");
       // pe.setFirstName("Chuyi");
        //pe.setLastName("Sun");
       // persons.add(pe);
    }
}
