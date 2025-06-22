package com.ecommerce.app.controller;


import com.ecommerce.app.model.dao.request.FavouriteForm;
import com.ecommerce.app.model.dao.response.AppResponse;
import com.ecommerce.app.model.dao.response.dto.FavouriteResponse;
import com.ecommerce.app.service.FavouriteService;
import com.ecommerce.app.utils.Enum.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favourites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class FavouriteController {

    private final FavouriteService favouriteService;


    @PostMapping("/toggle")
    public ResponseEntity<AppResponse<String>> toggleFavourite(@RequestBody FavouriteForm form) {
        favouriteService.toggleFavourite(form.getUserId(), form.getProductId());
        return ResponseEntity.ok(AppResponse.builderResponse(
                SuccessCode.UPDATED,
                ""
        ));
    }


    @GetMapping("/user/{uid}")
    public ResponseEntity<FavouriteResponse> getFavourites(@PathVariable Long uid) {
        FavouriteResponse response = favouriteService.getFavouriteByUser(uid);
        return ResponseEntity.ok(response);
    }
}

