package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class VendorServiceImplTest {
  @Mock
  VendorRepository vendorRepository;

  VendorService vendorService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
  }

  @Test
  public void testGetAllVendors() {
    // given
    List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

    // when
    when(vendorRepository.findAll()).thenReturn(vendors);

    List<VendorDTO> returnVendors = vendorService.getAllVendors();

    // then
    assertEquals(2, returnVendors.size());
  }

  @Test
  public void testGetVendorById() {
    // given
    Vendor vendor = new Vendor();
    vendor.setId(1L);
    vendor.setName("Yuan");

    // when
    when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

    VendorDTO returnVendorDTO = vendorService.getVendorById(1L);

    // then
    assertEquals("Yuan", returnVendorDTO.getName());
  }

  @Test
  public void testCreateVendor() {
    // given
    Vendor vendor = new Vendor();
    vendor.setName("Yuan");

    // when
    when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
    VendorDTO returnVendorDTO = vendorService.createVendor(new VendorDTO());

    // then
    assertEquals("Yuan", returnVendorDTO.getName());
  }

  @Test
  public void testUpdateVendor() {
    // given
    Vendor vendor = new Vendor();
    vendor.setName("Yuan");

    // when
    when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);
    VendorDTO vendorDTO = vendorService.updateVendor(new VendorDTO(), 1L);

    // then
    assertEquals("Yuan", vendorDTO.getName());
  }

  @Test
  public void testDeleteVendor() {

    // when
    vendorService.deleteVendor(1L);

    verify(vendorRepository, times(1)).deleteById(anyLong());
  }
}