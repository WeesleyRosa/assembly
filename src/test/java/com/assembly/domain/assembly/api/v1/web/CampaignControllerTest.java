/*package com.assembly.domain.assembly.api.v1.web;

import com.assembly.domain.assembly.api.v1.controller.AssemblyController;
import com.assembly.domain.assembly.service.AssemblyService;
import com.assembly.infra.common.enums.ErrorCodeEnum;
import com.assembly.infra.common.exception.GlobalExceptionHandler;
import com.assembly.infra.common.util.JsonUtil;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CampaignControllerTest {

    private static final String AUTHORIZATION_HEADER = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJDeDRQVUtFQlFZRmEyWU5FWXhjWG82V3c5Vmo2cXhwdm1QdHpGQVQ4YkM4In0.eyJqdGkiOiJkNmE5MWI1Yy0yNzNhLTRhMDAtYmYwZS04YTM2MjMwN2IzM2IiLCJleHAiOjE2MDU1NTgyMjgsIm5iZiI6MCwiaWF0IjoxNjA1NTUxMDI4LCJpc3MiOiJodHRwczovL2F1dGguZGV2LmludGVybmFsLnBoaXBhZ2FtZW50b3MuY29tL2F1dGgvcmVhbG1zL3BoaSIsInN1YiI6IjM4NDJhMTFlLTM0NzYtNDdkYy05YTA0LTg5ZDEzMzdlNTNiOSIsInR5cCI6IkJlYXJlciIsImF6cCI6Im1vYmlsZV9hcHBfZm9ydHBheSIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImQ2NzNmMzYxLTRhYWYtNDJhZS1iYjk3LTRiYWEzMjUwOGM2NyIsImFjciI6IjEiLCJzY29wZSI6IndhbGxldC1iYW5rLXNsaXAtY2FzaGluIGNhc2hiYWNrLWNhbXBhaWduLW1nbXQtYWRtaW4gd2FsbGV0LXRlZCBzaWdudXAtdXNlciBjYXNoYmFjay10cmFuc2FjdGlvbi1tZ210LXVzZXIgd2FsbGV0LWJpbGwtcGF5bWVudCB3YWxsZXQtdXNlciBwYXltZW50LXFyY29kZS11c2VyIGN1c3RvbWVyLWFkZHJlc3MgY2FzaGJhY2stdHJhbnNhY3Rpb24tbWdtdC1hZG1pbiB3YWxsZXQtY3JlZGl0LWNhcmQgY3VzdG9tZXItdXNlciBhdXRoLXVzZXIgY3VzdG9tZXItY2FyZCBjYXNoYmFjay1jYW1wYWlnbi1tZ210LXVzZXIiLCJjbGllbnRJZCI6Im1vYmlsZV9hcHBfZm9ydHBheSIsImNsaWVudEhvc3QiOiIxNzAuMjMxLjQ1LjIxOSIsImFwcGxpY2F0aW9uVVVJRCI6ImNlZGU3NWFlLTlkMjEtNDBkZC1iYTQyLTcyYTczNWJlMTIxNyIsInJlYWxtVVVJRCI6Ijk2MjE1NmQ3LTBmYWYtNDBhMC1hNDk4LTkzOWFmZjFkYjYyMSIsInJvbGVzIjpbImNhbXBhaWducy1jcmVhdGUiLCJjcmVhdGUtY2FzaGluLXRyYW5zZmVyIiwiYmFuay1zbGlwIiwid2FsbGV0LWJhbGFuY2UiLCJjYW1wYWlnbnMtdXBkYXRlIiwidHJhbnNhY3Rpb25zLWdldCIsImdldC11c2VyLWFjY2Vzc2tleSIsInRlZCIsInJlY292ZXItcGFzc3dvcmQiLCJwYXltZW50LXFyY29kZS1wYXJzZSIsImJpbGwtcGF5bWVudCIsImNhbXBhaWducy1nZXQtYWN0aXZlIiwid2FsbGV0LXRyYW5zZmVyIiwidXBkYXRlLXVzZXIiLCJiYW5rLWFjY291bnQiLCJjcmVhdGUtdXNlciIsInBheW1lbnQtcXJjb2RlLWdlbmVyYXRlIiwiY2FtcGFpZ25zLWdldCIsInVzZXItY2FyZC1tZ210IiwiYmFuay1zbGlwLWNhc2hpbiIsImdldC11c2VyIiwidHJhbnNhY3Rpb25zLWNyZWF0ZSIsImxvZ2luLXVzZXIiLCJ3YWxsZXQtc3RhdGVtZW50IiwidXNlci1hZGRyZXNzLW1nbXQiLCJ3YWxsZXQtYmFua3MiXSwicmVhbG0iOiJGT1JUUEFZIiwiY2xpZW50QWRkcmVzcyI6IjE3MC4yMzEuNDUuMjE5In0.bQAlQ8dgaM_oiLM9Pogr3f1nLzDn4x4Fi4Ygx4S3Fa_D-xda4EWVo_vIKh8W9EMS7rKrYwqnlF6HBuCMNLpQF2zq2CBwenFxJ52Ne1y-JjzNqtWEDH0mthwJYGIyF-v7-AJT-yV48ghl6lausWYPWJZZDZpVzDAu94wVyxk0Tcc4pUTeE03IjqfR1Njl4fE-JKT8Jf7LvXZM89xGVnmtYPqa452WMMRq8wQlVWVQP3irEIrDgmoNdXawTI21K8b7-1rAk7mXshEB75J6UvXLAMXthizxBon4k25mfUhGs-q-OneXQ5WhYOADEVLhBnN_B5iwOcrAEXsHkxbWo8ER4w";
    private static final String USER_INFO_HEADER = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJwM3MzaDZnOXdjMGxSbTBTYlZIMmg1ajEwclB2TXVSWVg0dFhfYlgxblVnIn0.eyJqdGkiOiJkN2Y0ZDE0Mi00OWJmLTQyOTMtOGZmMy01ZTNhNWJmMTc4M2IiLCJleHAiOjE2MDc0Mzk2NzcsIm5iZiI6MCwiaWF0IjoxNjA3NDMyNDc3LCJpc3MiOiJodHRwOi8va2V5Y2xvYWstaHR0cC5hdXRobnotZGV2ZWxvcDo4MDgwL2F1dGgvcmVhbG1zL0ZPUlRQQVkiLCJzdWIiOiI3NWJhNDE3MC01M2Q1LTQ1NjEtYmUzYy1kMzNiMzZkYjA5NjQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ1c2VyLWxvZ2luIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiMTVjODQ5MjktOGRhMy00Y2I0LWFkYWQtOTc0MDYxODFhYjk0IiwiYWNyIjoiMSIsInNjb3BlIjoiIiwidXNlcl9pZCI6Ijc1YmE0MTcwLTUzZDUtNDU2MS1iZTNjLWQzM2IzNmRiMDk2NCIsImdyb3VwcyI6WyJ1c2VyLWRlZmF1bHQiXSwicGxhdGZvcm0iOiJGb3J0UGF5In0.H5jSIPNMjHCstb8eU1Y4R8tS7wFp-UAsX2C8xUnQ00ua2M_DonBYbG-YN--jjxIWfa1Mki3e5YnMJ1F8hr9FlFQvIZk0n-kla1ysFE0YO3Z_zr19b8G-ZjvGzW5hLD5HMGcC-g8oFRxaWsArvoogy_vE0agJYxu1ifVDXHyuJBBWGk5L_UPKXgU6rWuC3jM0DrgBVezLC55elBTXJoODQX7s_t5qc0Lq19epy_DVzj1liIMINFVrtssxLzn15IRD6fwAUtx3nLHxQVWkw88u4f4cAMG6PN5mzv2Q9l2iTDO-YfrZkROIo8DdK_6p6z4Kl4vpDPspJt6Btxn3LUWpzQ";

    @InjectMocks
    private AssemblyController assemblyController;

    @Mock
    private AssemblyService assemblyService;

    private MockMvc mockMvc;

    @BeforeEach
    void initEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(assemblyController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Nested
    @DisplayName("Create Cashback Campaign")
    class Create {

        @DisplayName("Success - Successfully created a new cashback campaign")
        @Test
        void should_returnCampaignId_when_createdNewCampaign() throws Exception {
            CreateCampaignRequest createCampaignRequest = TestContextFactory.createCreateCampaignRequest();
            when(campaignService.create(any(CampaignBO.class), any(CashbackBO.class), any(PlatformBO.class), any(SponsorBO.class))).thenReturn(TestContextFactory.createCampaign());
            mockMvc.perform(post("/v1/campaigns")
                    .header(WebConstant.USER_INFO, USER_INFO_HEADER)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(createCampaignRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "http://localhost/v1/campaigns/1"));
        }


        @DisplayName("Failure - Failed to create new cashback campaign due to campaign name null")
        @Test
        void should_returnBadRequest_when_campaignNameIsNull() throws Exception {
            CreateCampaignRequest createCampaignRequest = TestContextFactory.createCreateCampaignRequest();
            createCampaignRequest.setName(null);
            mockMvc.perform(post("/v1/campaigns")
                    .header(WebConstant.USER_INFO, USER_INFO_HEADER)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(createCampaignRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code", is(ErrorCodeEnum.INVALID_REQUEST_BODY.getValue())));
        }

        @DisplayName("Failure - Failed to create new cashback campaign due to startsAt field being less than the current date plus one day (d+1)")
        @Test
        void should_returnBadRequest_when_campaignStartDateIsNotValid() throws Exception {
            CreateCampaignRequest createCampaignRequest = TestContextFactory.createCreateCampaignRequest();
            createCampaignRequest.setStartsAt(LocalDate.now());
            mockMvc.perform(post("/v1/campaigns")
                    .header(WebConstant.USER_INFO, USER_INFO_HEADER)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(createCampaignRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code", is(ErrorCodeEnum.INVALID_REQUEST_BODY.getValue())));
        }

    }

    @Nested
    @DisplayName("Retrieve Specific Cashback Campaign")
    class RetrieveSpecificCampaign {

        @DisplayName("Success - Successfully retrieved an existing campaign")
        @Test
        void should_returnCampaign_when_campaignIdReceived() throws Exception {
            when(campaignService.retrieve(anyLong())).thenReturn(TestContextFactory.createCampaign());
            mockMvc.perform(get("/v1/campaigns/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)));
        }

        @DisplayName("Failure - Failed to find a campaign for the given id")
        @Test
        void should_returnBadRequest_when_campaignNotFound() throws Exception {
            when(campaignService.retrieve(anyLong())).thenThrow(new CampaignNotFoundException());
            mockMvc.perform(get("/v1/campaigns/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code", is(ErrorCodeEnum.NO_DATA_FOUND.getValue())));
        }

    }

    @Nested
    @DisplayName("Retrieve Active Cashback Campaign")
    class RetrieveActiveCampaigns {

        @DisplayName("Success - Successfully retrieved the current active campaign for a giving platform")
        @Test
        void should_returnActiveCampaign_when_existsActiveCampaign() throws Exception {
            when(campaignService.findByPlatformNameAndPaymentType(anyString(), any())).thenReturn(TestContextFactory.createCampaign());
            mockMvc.perform(get("/v1/campaigns/active")
                    .header(WebConstant.USER_INFO, userInfo)
                    .param("paymentType", PaymentTypeEnum.PAYMENT_SLIP.getValue())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)));
        }

        @DisplayName("Failure - Failed to find any active campaign for a giving platform")
        @Test
        void should_returnBadRequest_when_doesNotexistActiveCampaign() throws Exception {
            when(campaignService.findByPlatformNameAndPaymentType(anyString(), any())).thenThrow(new CampaignNotFoundException());
            mockMvc.perform(get("/v1/campaigns/active")
                    .header(WebConstant.USER_INFO, userInfo)
                    .param("paymentType", PaymentTypeEnum.PAYMENT_SLIP.getValue())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code", is(ErrorCodeEnum.NO_DATA_FOUND.getValue())));
        }

    }

    @Nested
    @DisplayName("Retrieve All Campaigns of a Giving Platform")
    class RetrieveAllCampaigns {

        @DisplayName("Success - Successfully retrieved all campaigns of a giving platform")
        @Test
        void should_returnAllCampaignsOfGivingPlatform_when_existCampaigns() throws Exception {
            when(campaignService.retrieve(anyString(), any(), any())).thenReturn(new PageImpl<>(List.of(TestContextFactory.createCampaign())));
            mockMvc.perform(get("/v1/campaigns")
                    .header(WebConstant.USER_INFO, USER_INFO_HEADER)
                    .param("status", CampaignStatusEnum.CREATED.getValue())
                    .param("page", "1")
                    .param("size", "10")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

    }

    @Nested
    @DisplayName("Suspend Campaign for Giving Id")
    class Suspend {

        @DisplayName("Success - Successfully suspended a giving campaign")
        @Test
        void should_suspendAndReturnNoContent_when_existsActiveCampaign() throws Exception {
            doNothing().when(campaignService).suspend(anyLong());
            mockMvc.perform(put("/v1/campaigns/1/suspend")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @DisplayName("Failure - Failed to find any campaigns to suspend for the giving id")
        @Test
        void should_returnBadRequest_when_doesNotExistCampaign() throws Exception {
            doThrow(CampaignNotFoundException.class).when(campaignService).suspend(anyLong());
            mockMvc.perform(put("/v1/campaigns/1/suspend")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

    }

    @Nested
    @DisplayName("Reschedule Cashback Campaign")
    class Reschedule {

        @DisplayName("Success - Successfully rescheduled a new cashback campaign")
        @Test
        void should_rescheduleAndReturnNoContent_when_existsActiveCampaign() throws Exception {
            UpdateDueDateRequest updateDueDateRequest = TestContextFactory.createUpdateDueDateRequest();
            doNothing().when(campaignService).reschedule(anyLong(), any(CampaignBO.class));
            mockMvc.perform(patch("/v1/campaigns/1/reschedule")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(updateDueDateRequest)))
                    .andExpect(status().isNoContent());
        }

        @DisplayName("Failure - Failed to reschedule a new cashback campaign")
        @Test
        void should_returnBadRequest_when_endsAtDateIsNotValid() throws Exception {
            UpdateDueDateRequest updateDueDateRequest = TestContextFactory.createUpdateDueDateRequest();
            updateDueDateRequest.setEndsAt(LocalDate.now());
            mockMvc.perform(patch("/v1/campaigns/1/reschedule")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(updateDueDateRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code", is(ErrorCodeEnum.INVALID_REQUEST_BODY.getValue())));
        }

    }

    @Nested
    @DisplayName("Modify Cashback Campaign")
    class Modify {

        @DisplayName("Success - Successfully modified an cashback campaign")
        @Test
        void should_modifyAndReturnNoContent_when_existsCampaign() throws Exception {
            ModifyCampaignRequest modifyCampaignRequest = TestContextFactory.createModifyCampaignRequest();
            doNothing().when(campaignService).modify(anyLong(), any(CampaignBO.class), any(CashbackBO.class));
            mockMvc.perform(patch("/v1/campaigns/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(modifyCampaignRequest)))
                    .andExpect(status().isNoContent());
        }

        @DisplayName("Failure - Failed to modify an cashback campaign")
        @Test
        void should_returnBadRequest_when_endsAtDateIsNotValid() throws Exception {
            ModifyCampaignRequest modifyCampaignRequest = TestContextFactory.createModifyCampaignRequest();
            modifyCampaignRequest.setEndsAt(LocalDate.now());
            mockMvc.perform(patch("/v1/campaigns/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(modifyCampaignRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code", is(ErrorCodeEnum.INVALID_REQUEST_BODY.getValue())));
        }

    }

    static class TestContextFactory {

        private static final Faker FAKER = new Faker(new Locale("pt-BR"));

        public static SponsorRequest createSponsorRequest() {
            return SponsorRequest.builder()
                    .document("93833615060")
                    .build();
        }

        public static CashbackRequest createCashbackRequest() {
            return CashbackRequest.builder()
                    .maximumPaymentValue(500L)
                    .percentage(new BigDecimal("0.1"))
                    .build();
        }

        public static CreateCampaignRequest createCreateCampaignRequest() {
            return CreateCampaignRequest.builder()
                    .name("Test Cashback")
                    .startsAt(LocalDate.now().plusDays(1))
                    .endsAt(LocalDate.now().plusMonths(1))
                    .cashback(createCashbackRequest())
                    .sponsor(createSponsorRequest())
                    .paymentType(PaymentTypeEnum.PAYMENT_SLIP)
                    .build();
        }

        public static Platform createPlatform() {
            return Platform.builder()
                    .id(1L)
                    .name("FortPay")
                    .balanceType("FortPay")
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        public static Sponsor createSponsor() {
            return Sponsor.builder()
                    .id(332040L)
                    .document("93833615060")
                    .platform(createPlatform())
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        public static Cashback createCashback() {
            return Cashback.builder()
                    .id(1L)
                    .maximumPaymentValue(500L)
                    .percentage(new BigDecimal("0.1"))
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        public static Campaign createCampaign() {
            return Campaign.builder()
                    .id(1L)
                    .name("Test Cashback")
                    .startsAt(LocalDate.now())
                    .endsAt(LocalDate.now().plusMonths(1))
                    .sponsor(createSponsor())
                    .cashback(createCashback())
                    .status(CampaignStatusEnum.CREATED)
                    .createdAt(LocalDateTime.now())
                    .paymentType(PaymentTypeEnum.PAYMENT_SLIP)
                    .build();
        }

        public static UpdateDueDateRequest createUpdateDueDateRequest() {
            return UpdateDueDateRequest.builder()
                    .endsAt(LocalDate.now().plusDays(1))
                    .build();
        }

        public static ModifyCampaignRequest createModifyCampaignRequest() {
            return ModifyCampaignRequest.builder()
                    .name("teste")
                    .startsAt(LocalDate.now().plusDays(1))
                    .endsAt(LocalDate.now().plusDays(1))
                    .cashback(createCashbackRequest())
                    .build();
        }

    }

}
*/