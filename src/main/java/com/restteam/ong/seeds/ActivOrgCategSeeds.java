package com.restteam.ong.seeds;

import java.util.List;

import com.restteam.ong.controllers.dto.ActivityRequest;
import com.restteam.ong.controllers.dto.CategoryRequest;
import com.restteam.ong.controllers.dto.OrganizationCreateDTO;
import com.restteam.ong.models.Activity;
import com.restteam.ong.models.Categories;
import com.restteam.ong.models.Organization;
import com.restteam.ong.repositories.ActivityRepository;
import com.restteam.ong.repositories.CategoriesRepository;
import com.restteam.ong.repositories.OrganizationRepository;
import com.restteam.ong.services.ActivityService;
import com.restteam.ong.services.CategoriesService;
import com.restteam.ong.services.impl.OrganizationServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.net.SyslogOutputStream;

@Component
public class ActivOrgCategSeeds implements CommandLineRunner {

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public void run(String... args) {
        this.seedOrganization();
        this.seedCategory();
        this.seedActivity();
    }

    private void seedOrganization() {

        List<Organization> organizations = this.organizationRepository.findAll();

        if (organizations.isEmpty()) {
            // crear organizacion
            System.out.println("**********CREATING ORGANIZATIONS********");
            OrganizationCreateDTO org1 = new OrganizationCreateDTO("Patitas", "Patitas.png", "perrito 123", 118263537,
                    "patitas@gmail.com", "Bienvenido a Patitas",
                    "Nos ocupamos principalmente de satisfacer las necesidades basicas de los barrios de zona sur que no son alcanzados por los servicios estatales humanitarios",
                    "http://facebook.com/patitasONG/", "http://instagram.com/patitasONG/",
                    "http://linkedin.com/patitasONG/");

            OrganizationCreateDTO org2 = new OrganizationCreateDTO("Un sol para los chicos", "sol.png",
                    "callefalsa 123", 8001234, "unsol@chicos.com", "Bien venido al sol",
                    "Nuestra misión en el mundo es que no exista un solo chico que pase hambre en su etapa de desarrollo",
                    "http://facebook.com/unSol/", "http://instagram.com/unSol/", "http://linkedin.com/unSol/");

            OrganizationCreateDTO org3 = new OrganizationCreateDTO("Caritas", "caras.png", "av. siempreviva 123",
                    1800333, "caritas@outlook.com", "Bien venido a Caritas",
                    "Cada dia son mas las familias que logran salir de la pobreza con la ayuda integral de Caritas. Acompañanos a crecer juntos.",
                    "http://facebook.com/caritas/", " http://instagram.com/caritas/", "http://linkedin.com/caritas/");

            OrganizationCreateDTO org4 = new OrganizationCreateDTO("Cachabamba", "cachau.png", "Rivadavia 12321",
                    48002277, "cachabamba_rivadavia@hotmail.com", "Bien venido a Cachabamba",
                    "Centro de recreación deportiva para la inclusión y el bienestar general",
                    "http://facebook.com/cachabamba/", "http://instagram.com/cachabamba/",
                    "http://linkedin.com/cachabamba/");

            OrganizationCreateDTO org5 = new OrganizationCreateDTO("Programadores sin frontera", "consola.png",
                    "bytecode 0101", 01101001, "hello@word.net", "Bien venido a Programadores sin frontera",
                    "Organizacion sin fines de lucro cuyo fin es el de desarrollar y mantener la infraestructura virtual de todas aquellas organizaciones que se ocupen de realizar un bien a la sociedad, para que éstas puedan ampliar sus horizontes.",
                    "http://facebook.com/prosinfro/", "http://instagram.com/prosinfro/",
                    "http://linkedin.com/prosinfro/");

            OrganizationServiceImpl service = new OrganizationServiceImpl();
            this.organizationRepository.save(service.complete(org1));
            this.organizationRepository.save(service.complete(org2));
            this.organizationRepository.save(service.complete(org3));
            this.organizationRepository.save(service.complete(org4));
            this.organizationRepository.save(service.complete(org5));
        } else {
            System.out.println("*******ORGANIZATIONS EXIST*********");
        }

    }

    private void seedCategory() {

        List<Categories> categories = this.categoriesRepository.findAll();

        if (categories.isEmpty()) {
            // create categories¨
            System.out.println("***********CREATING CATEGORIES********");

            CategoryRequest categ1 = new CategoryRequest("Arte",
                    "El arte se trata de organizaciones cuyo fin es alentar el desarrollo", "obra_de_arte.png");
            CategoryRequest categ2 = new CategoryRequest("Deporte",
                    "El deporte cubre todas las organizaciones cuyo fin es alentar a los niños a desarrollar actividad fisica.",
                    "futbol.png");
            CategoryRequest categ3 = new CategoryRequest("Caridad",
                    "descripcion: La caridad trata de organizaciones cuyo fin es la recaudacion de bienes para ayudar a la poblacion.",
                    "ayuda.png");

            CategoriesService service= new CategoriesService();
            this.categoriesRepository.save(service.complete(categ1));
            this.categoriesRepository.save(service.complete(categ2));
            this.categoriesRepository.save(service.complete(categ3));

        } else {
            System.out.println("**********CATEGORIES EXISTS********");
        }
 
    }

    private void seedActivity() {
        List<Activity> activities = (List<Activity>)this.activityRepository.findAll();

        if(activities.isEmpty()){
            //create activities
            System.out.println("************CREATING ACTIVITIES***********");
            ActivityRequest act1= new ActivityRequest("Construccion de casas", "Construccion de viviendas para familias en situación de calle", "casas.png");
            ActivityRequest act2= new ActivityRequest("Limpieza de calles", "Limpieza de calles en zonas con poca ayuda estatal", "calles.png");
            ActivityRequest act3= new ActivityRequest("Junta de alimentos", "Recoleccion de alimentos para los ms necesitados de los barrios humildes", "alimentos.png");
            ActivityRequest act4= new ActivityRequest("Entrega nocturna de café", "Reparto de café en horarios nocturnos para gente de bajos recursos", "cafe.png");
            ActivityRequest act5= new ActivityRequest("Colecta de ropa", "Junta de ropa para entregar en las provincias aledañas", "ropa.png");
            ActivityRequest act6= new ActivityRequest("Rifa de picada", "Rifa de picada para cuatro personas con el fin de juntar donaciones", "rifa.png");
            ActivityRequest act7= new ActivityRequest("Torneo de futbol", "torneo de futbol 8 para jovenes enre 7 y 11 años", "fotbol.png");
            ActivityRequest act8= new ActivityRequest("Torneo de truco", "torneo de truco para adultos mayores de 18 años", "truco.png");

            ActivityService service= new ActivityService();
            this.activityRepository.save(service.complete(act1));
            this.activityRepository.save(service.complete(act2));
            this.activityRepository.save(service.complete(act3));
            this.activityRepository.save(service.complete(act4));
            this.activityRepository.save(service.complete(act5));
            this.activityRepository.save(service.complete(act6));
            this.activityRepository.save(service.complete(act7));
            this.activityRepository.save(service.complete(act8));

        }else{
            System.out.println("**********ACTIVITIES EXIST*************");
        }

    }

}
