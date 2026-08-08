package com.bank.cards.service;

import com.bank.cards.dto.CardsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;

@Service
public interface CardsService {
    void createCard(@Valid @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);

    CardsDTO fetchCard(@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);

    boolean updateCard(@Valid CardsDTO cardsDto);

    boolean deleteCard(@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);
}
