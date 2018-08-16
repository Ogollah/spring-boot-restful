package com.springbootrest.springbootrestful.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    @GetMapping("V1/person")
    public PersonV1 persV1(){
        return new PersonV1("Money Man");
    }

    @GetMapping("v2/person")
    public PersonV2 persV2() {
        return new PersonV2(new Name("Janet", "Mbugua"));}

        //parameter versioning
        @GetMapping(value = "/person/param", params = "version=1")
        public PersonV1 person () {
            return new PersonV1("Money Man");
        }

        @GetMapping(value = "/person/param", params = "version=2")
        public PersonV2 personV2 () {
            return new PersonV2(new Name("Janet", "Mbugua"));
        }

        //header versioning
        @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
        public PersonV1 headerV1 () {
            return new PersonV1("Money Man");
        }

        @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
        public PersonV2 headerV2 () {
            return new PersonV2(new Name("Janet", "Mbugua"));
        }

        //produces versioning (accept header)
        @GetMapping(value = "/person/produces", produces = "application/vnd.company.app-V1+json")
        public PersonV1 producesV1 () {
            return new PersonV1("Money Man");
        }

        @GetMapping(value = "/person/produces", produces = "application/vnd.company.app-V2+json")
        public PersonV2 producesV2 () {
            return new PersonV2(new Name("Janet", "Mbugua"));
        }
    }
