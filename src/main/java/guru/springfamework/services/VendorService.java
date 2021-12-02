package guru.springfamework.services;


import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
  List<VendorDTO> getAllVendors();
  VendorDTO getVendorById(Long id);
  VendorDTO createVendor(VendorDTO vendor);
  VendorDTO updateVendor(VendorDTO vendor, Long id);
  VendorDTO patchVendor(VendorDTO vendor, Long id);
  void deleteVendor(Long id);
}
