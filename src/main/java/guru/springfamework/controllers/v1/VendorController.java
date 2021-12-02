package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
  public static final String BASE_URL = "/api/v1/shop/vendors";

  private final VendorService vendorService;

  public VendorController(VendorService vendorService) {
    this.vendorService = vendorService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public VendorListDTO getAllVendors() {
    return new VendorListDTO(vendorService.getAllVendors());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
    return vendorService.createVendor(vendorDTO);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteVendor(@PathVariable Long id) {
    vendorService.deleteVendor(id);
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public VendorDTO getVendorById(@PathVariable Long id) {
    return vendorService.getVendorById(id);
  }

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
    return vendorService.patchVendor(vendorDTO, id);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
    return vendorService.updateVendor(vendorDTO, id);
  }
}
