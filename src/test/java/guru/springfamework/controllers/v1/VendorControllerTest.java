package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {
  @Mock
  VendorService vendorService;

  VendorController vendorController;

  MockMvc mockMvc;

  VendorDTO vendorDTO1;
  VendorDTO vendorDTO2;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    vendorController = new VendorController(vendorService);

    mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();

    vendorDTO1 = new VendorDTO();
    vendorDTO1.setName("Yuan");
    vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/1");

    vendorDTO2 = new VendorDTO();
    vendorDTO2.setName("Nan");
    vendorDTO2.setVendorUrl(VendorController.BASE_URL + "/2");
  }

  @Test
  public void testGetAllVendors() throws Exception{
    // when
    when(vendorService.getAllVendors()).thenReturn(Arrays.asList(new VendorDTO(), new VendorDTO()));

    // then
    mockMvc.perform(get(VendorController.BASE_URL + "/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.vendors", hasSize(2)));
  }

  @Test
  public void testCreateVendor() throws Exception{
    // when
    when(vendorService.createVendor(any(VendorDTO.class))).thenReturn(vendorDTO1);

    mockMvc.perform(post(VendorController.BASE_URL + "/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())))
            .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO1.getVendorUrl())));
  }

  @Test
  public void testDeleteVendor() throws Exception{
    mockMvc.perform(delete(VendorController.BASE_URL + "/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    verify(vendorService, times(1)).deleteVendor(anyLong());
  }

  @Test
  public void testGetVendorById() throws Exception{
    // when
    when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO2);

    // then
    mockMvc.perform(get(VendorController.BASE_URL + "/2")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo(vendorDTO2.getName())))
            .andExpect(jsonPath("$.vendor_url", equalTo(vendorDTO2.getVendorUrl())));
  }

  @Test
  public void testUpdateVendor() throws Exception{

    when(vendorService.updateVendor(any(VendorDTO.class), anyLong())).thenReturn(vendorDTO2);

    mockMvc.perform(put(VendorController.BASE_URL + "/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(new VendorDTO())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo(vendorDTO2.getName())));
  }

  @Test
  public void testPatchVendor() throws Exception{
    when(vendorService.patchVendor(any(VendorDTO.class), anyLong())).thenReturn(vendorDTO2);

    mockMvc.perform(patch(VendorController.BASE_URL + "/2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(new VendorDTO())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo(vendorDTO2.getName())));
  }
}