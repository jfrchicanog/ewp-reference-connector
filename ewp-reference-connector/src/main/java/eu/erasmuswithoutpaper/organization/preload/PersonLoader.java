
package eu.erasmuswithoutpaper.organization.preload;

import eu.erasmuswithoutpaper.internal.AbstractStartupLoader;
import eu.erasmuswithoutpaper.internal.JsonHelper;
import eu.erasmuswithoutpaper.organization.entity.Person;
import java.io.IOException;

public class PersonLoader extends AbstractStartupLoader {
    
    @Override
    public void createDemoDataIkea() throws IOException {
        
        //IKEA
        persistPerson("{'personId':'9001013344','firstNames':'Billy','lastName':'Thomas','birthDate':'1990-01-01','gender':'MALE','countryCode':'SE'}");
        persistPerson("{'personId':'9011046365','firstNames':'Kate Alice','lastName':'Moneypenny','birthDate':'1990-11-04','gender':'FEMALE'}");
        persistPerson("{'personId':'8704122398','firstNames':'Carl Henry','lastName':'Simson','birthDate':'1987-04-12','gender':'MALE','countryCode':'SE'}");
        persistPerson("{'personId':'8906093845','firstNames':'Mary','lastName':'Carter','birthDate':'1989-06-09','gender':'FEMALE'}");
        persistPerson("{'personId':'9107146991','firstNames':'Emily Nicole','lastName':'Morgan','birthDate':'1991-07-14','gender':'FEMALE','countryCode':'SE'}");
    }

    @Override
    public void createDemoDataPomodoro() throws IOException {
        //Pomodoro
        persistPerson("{'personId':'8810126789','firstNames':'Ann Paige','lastName':'White','birthDate':'1988-10-12','gender':'FEMALE','countryCode':'IT'}");
        persistPerson("{'personId':'8712146574','firstNames':'Kirk Gregory','lastName':'Willis','birthDate':'1987-12-14','gender':'MALE'}");
        persistPerson("{'personId':'9003228402','firstNames':'Arnold','lastName':'Jones','birthDate':'1990-03-22','gender':'MALE','countryCode':'IT'}");
        persistPerson("{'personId':'8602181287','firstNames':'Sharon Hannah','lastName':'Lopez','birthDate':'1986-02-18','gender':'FEMALE'}");
        persistPerson("{'personId':'9104125620','firstNames':'Lucy','lastName':'Roberts','birthDate':'1991-04-12','gender':'FEMALE','countryCode':'IT'}");
    }
    
    private void persistPerson(String personJson) throws IOException {
        Person person = JsonHelper.mapToObject(Person.class, personJson);
        em.persist(person);
    }

	@Override
	public void createDemoDataUma() throws IOException {
		//UMA
        persistPerson("{'personId':'9001013344','firstNames':'Lilian','lastName':'Barranco','birthDate':'1988-10-12','gender':'FEMALE','countryCode':'ES'}");
        persistPerson("{'personId':'9011046365','firstNames':'Virginia','lastName':'Escriche','birthDate':'1987-12-14','gender':'MALE'}");
        persistPerson("{'personId':'8704122398','firstNames':'Teresa','lastName':'Rojas','birthDate':'1990-03-22','gender':'MALE','countryCode':'ES'}");
        persistPerson("{'personId':'8906093845','firstNames':'Ricardo','lastName':'del Milagro','birthDate':'1986-02-18','gender':'FEMALE'}");
        persistPerson("{'personId':'9107146991','firstNames':'Margarita','lastName':'Delgado','birthDate':'1991-04-12','gender':'FEMALE','countryCode':'ES'}");
	}
}
