package fr.diginamic.gestit_back.service;

import fr.diginamic.gestit_back.dto.AdresseDto;
import fr.diginamic.gestit_back.dto.CovoiturageDtoRecord;
import fr.diginamic.gestit_back.entites.Adresse;
import fr.diginamic.gestit_back.entites.Commune;
import fr.diginamic.gestit_back.entites.Covoiturage;
import fr.diginamic.gestit_back.repository.AdresseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Data
public class AdresseService {

    private AdresseRepository adresseRepository;

    private CommuneService communeService;

    @Transactional
    public void nouvelleAdresse(AdresseDto adresseDto) {
        // Création nouvelle adresse
        Adresse nouvelleAdresse = new Adresse(adresseDto.numero(), adresseDto.voie());
        // Récupération de la commune en basse
        Commune commune = this.communeService.verifierExistenceCommune(adresseDto.commune(), adresseDto.codePostal());
        // Si la commune n'existe pas, création et insertion en base
        if (commune == null) {
            commune = communeService.nouvelleCommune(new Commune(adresseDto.commune(), adresseDto.codePostal()));
        }
        // Ajout de la commune à l'adresse
        nouvelleAdresse.setCommune(commune);
        // Sauvegarde de l'adresse
        this.adresseRepository.save(nouvelleAdresse);
    }


    public AdresseDto changeToAdresseDto(Adresse adresse){

        return new AdresseDto(
                adresse.getId(),
                adresse.getCommune().getNom(),
                adresse.getCommune().getCodePostal(),
                adresse.getNumero(),
                adresse.getVoie());
    }

    public Adresse changeToAdresse(AdresseDto adresseDto){
        return new Adresse(
                adresseDto.numero(),
                adresseDto.voie(),
                new Commune(adresseDto.commune(), adresseDto.codePostal()));
    }
}
