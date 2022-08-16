package com.techelevator.ETL;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class LoadAPIToDatabase {

    public static void main(String[] args) {
        helper newHelper = new helper();
        //String streetName = newHelper.getAddress() +" "+newHelper.getStreetName();
        //System.out.println(streetName);
        newHelper.createEntries();
    }

    static class helper{
        String template = "INSERT INTO rental_property (rental_address,rental_amount,bathrooms,bedrooms,is_rented,type_of_residence, description, picture, landlord_id) VALUES ('";
        private static final String API_BASE_URL = "https://random-word-api.herokuapp.com/word";
        private static final String API_BASE_MASHVISOR1 = "https://mashvisor-api.p.rapidapi.com/airbnb-property/newly-listed?state=PA&city=Pittsburgh&page=2";
        private static final String API_BASE_MASHVISOR2 = "https://mashvisor-api.p.rapidapi.com/airbnb-property/newly-listed?state=PA&city=Pittsburgh&page=3";
        private static final String API_BASE_MASHVISOR3 = "https://mashvisor-api.p.rapidapi.com/airbnb-property/newly-listed?state=PA&city=Pittsburgh&page=4";
        private static final String API_BASE_MASHVISOR4 = "https://mashvisor-api.p.rapidapi.com/airbnb-property/newly-listed?state=PA&city=Pittsburgh&page=5";
        private final RestTemplate restTemplate = new RestTemplate();

        public void createEntries()
        {
            List<String> desc = createDescription();
            List<String> pics = createPicss();
            List<String> baths = createBaths();
            List<String> beds = createBedrooms();
            List<String> price = createPrice();
            List<String> zips = createZips();
            String dataFile = "inserts.txt";
            System.out.println(desc.size() +" "+pics.size() +" "+baths.size() +" "+beds.size() +" "+price.size() +" "+zips.size());
            System.out.flush();
            Random rand = new Random();
            try (PrintWriter dataOutput = new PrintWriter(new FileOutputStream(new File(dataFile), true))) {
                for (int i = 0; i < pics.size(); i++) {
                    dataOutput.println(template +getAddress() + " "+ getStreetName()+zips.get(i)+"', "+price.get(i)+"0.00, "+baths.get(i)+", "+beds.get(i)+", false, 'apartment', '"+desc.get(i)+"', '"+pics.get(i)+"', "+rand.nextInt(4)+");");
                }
            }catch (FileNotFoundException e)
            {
                System.out.println("nope");
            }
        }

        public List<String> createDescription()
        {
            String dataFile = "apidump.txt";
            boolean desc = true;
            boolean in = false;
            String temp = "";
            List<String> description = new ArrayList<>();
            String line = "";
            try(Scanner input = new Scanner(new File(dataFile))){

                    while(input.hasNext())
                    {
                        line = input.nextLine();
                        if(!in){
                            if(line.contains("description="))
                            {
                                in = true;
                                temp = line.substring(line.indexOf("description=")+12);
                            }
                            if(line.contains("description_locale="))
                            {
                                //System.out.println("total length: "+temp.length());
                                if(temp.indexOf("description_locale=") > 0) {
                                    temp = temp.substring(0, temp.indexOf("description_locale="));
                                    description.add(temp);
                                }
                            }
                        }
                        else{
                            if(line.contains("description_locale="))
                            {
                                temp = line.substring(0, line.indexOf("description_locale="));
                                description.add(temp);
                                in = false;
                            }
                            else {
                                temp = temp + line;
                            }
                        }
                    }

            }catch (FileNotFoundException e){
                System.out.println("nope!");
            }
            return description;
        }

        public List<String> createPrice()
        {
            String dataFile = "apidump.txt";
            boolean desc = true;
            boolean in = false;
            String temp = "";
            List<String> description = new ArrayList<>();
            String line = "";
            try(Scanner input = new Scanner(new File(dataFile))){
                while(input.hasNext())
                    {
                        line = input.nextLine();
                        if(!in){
                            if(line.contains("price="))
                            {
                                in = true;
                                temp = line.substring(line.indexOf("price=")+6, line.indexOf(",", line.indexOf("price=")+6));
                                description.add(temp);
                                in = false;
                            }
                        }
                    }

            }catch (FileNotFoundException e){
                System.out.println("nope!");
            }
            return description;
        }

        public List<String> createBedrooms()
        {
            String dataFile = "apidump.txt";
            boolean desc = true;
            boolean in = false;
            String temp = "";
            List<String> description = new ArrayList<>();
            String line = "";
            String search = "bedrooms=";
            try(Scanner input = new Scanner(new File(dataFile))){
                while(input.hasNext())
                    {
                        line = input.nextLine();
                        if(!in){
                            if(line.contains("bedrooms="))
                            {
                                in = true;
                                temp = line.substring(line.indexOf(search)+search.length(), line.indexOf(",", line.indexOf(search)+search.length()));
                                description.add(temp);
                                in = false;
                            }
                        }
                    }

            }catch (FileNotFoundException e){
                System.out.println("nope!");
            }
            return description;
        }

        public List<String> createBaths()
        {
            String dataFile = "apidump.txt";
            boolean desc = true;
            boolean in = false;
            String temp = "";
            List<String> description = new ArrayList<>();
            String line = "";
            String search = "bathrooms=";
            try(Scanner input = new Scanner(new File(dataFile))){
                while(input.hasNext())
                    {
                        line = input.nextLine();
                        if(!in){
                            if(line.contains(search))
                            {
                                in = true;
                                temp = line.substring(line.indexOf(search)+search.length(), line.indexOf(",", line.indexOf(search)+search.length()));
                                description.add(temp);
                                in = false;
                            }
                        }
                    }

            }catch (FileNotFoundException e){
                System.out.println("nope!");
            }
            return description;
        }

        public List<String> createZips()
        {
            String dataFile = "apidump.txt";
            boolean desc = true;
            boolean in = false;
            String temp = "";
            List<String> description = new ArrayList<>();
            String line = "";
            String search = "zipcode=";
            try(Scanner input = new Scanner(new File(dataFile))){
                while(input.hasNext())
                    {
                        line = input.nextLine();
                        if(!in){
                            if(line.contains(search))
                            {
                                in = true;
                                temp = line.substring(line.indexOf(search)+search.length(), line.indexOf(",", line.indexOf(search)+search.length()));
                                description.add(temp);
                                in = false;
                            }
                        }

                }
            }catch (FileNotFoundException e){
                System.out.println("nope!");
            }
            return description;
        }

        public List<String> createPicss()
        {
            String dataFile = "apidump.txt";
            boolean desc = true;
            boolean in = false;
            String temp = "";
            List<String> description = new ArrayList<>();
            String line = "";
            String search = "xl_picture_url=";
            try(Scanner input = new Scanner(new File(dataFile))){
                while(input.hasNext())
                    {
                        line = input.nextLine();
                        if(!in){
                            if(line.contains(search))
                            {
                                in = true;
                                temp = line.substring(line.indexOf(search)+search.length(), line.indexOf(",", line.indexOf(search)+search.length()));
                                description.add(temp);
                                in = false;
                            }
                        }
                    }

            }catch (FileNotFoundException e){
                System.out.println("nope!");
            }
            return description;
        }

        public int getAddress()
        {
            Random rand = new Random();
            return rand.nextInt(1000);
        }
        public String getStreetName()
        {
            String test = restTemplate.getForObject(API_BASE_URL, String.class).replace("[","").replace("\"", "").replace("]", "");
            test = test + " Street, Pittsburgh, Pa ";
            return test;
        }
        public void testMethod()
        {
            String description;
            BigDecimal price;
            String bedrooms;
            String bathrooms;
            String zip;
            String picture;
            boolean isRented;
            String typeOfResidence;
            String landlordID;

            //Set the headers you need send
            final HttpHeaders headers = new HttpHeaders();
            headers.set("X-RapidAPI-Key", "8541321054msh76f317c3cac5b10p15809ajsn731307344be9");
            headers.add("X-RapidAPI-Host", "mashvisor-api.p.rapidapi.com");

            //Create a new HttpEntity
            final HttpEntity<String> entity = new HttpEntity<String>(headers);

            //Execute the method writing your HttpEntity to the request
            //description = response.getBody().get("description").toString();
            //System.out.println(description);
            String dataFile = "apidump.txt";
            try (PrintWriter dataOutput = new PrintWriter(new FileOutputStream(new File(dataFile), true))){
                ResponseEntity<Map> response = restTemplate.exchange(API_BASE_MASHVISOR2, HttpMethod.GET, entity, Map.class);
                dataOutput.println(response.getBody());
                response = restTemplate.exchange(API_BASE_MASHVISOR3, HttpMethod.GET, entity, Map.class);
                dataOutput.println(response.getBody());
                response = restTemplate.exchange(API_BASE_MASHVISOR4, HttpMethod.GET, entity, Map.class);
                dataOutput.println(response.getBody());
                response = restTemplate.exchange(API_BASE_MASHVISOR1, HttpMethod.GET, entity, Map.class);
                dataOutput.println(response.getBody());
            }catch (FileNotFoundException e)
            {
                System.out.println("Nope");
            }
        }

    }

}
