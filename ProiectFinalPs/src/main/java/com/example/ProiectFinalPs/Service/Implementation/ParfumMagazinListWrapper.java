package com.example.ProiectFinalPs.Service.Implementation;

import com.example.ProiectFinalPs.Model.ParfumMagazin;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "parfumMagazinList")
public class ParfumMagazinListWrapper {

    private List<ParfumMagazin> parfumMagazins;

    public ParfumMagazinListWrapper() {
    }

    public ParfumMagazinListWrapper(List<ParfumMagazin> parfumMagazins) {
        this.parfumMagazins = parfumMagazins;
    }

    @XmlElement(name = "parfumMagazin")
    public List<ParfumMagazin> getParfumMagazins() {
        return parfumMagazins;
    }

    public void setParfumMagazins(List<ParfumMagazin> parfumMagazins) {
        this.parfumMagazins = parfumMagazins;
    }
}