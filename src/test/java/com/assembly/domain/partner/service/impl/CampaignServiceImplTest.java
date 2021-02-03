/*package com.assembly.domain.partner.service.impl;

import com.github.javafaker.Faker;
import com.phi.loyalty.cashback.domain.campaign.business.CampaignBO;
import com.phi.loyalty.cashback.domain.campaign.business.exception.CampaignNotFoundException;
import com.phi.loyalty.cashback.domain.campaign.business.exception.InvalidEffectiveDateException;
import com.phi.loyalty.cashback.domain.campaign.entity.Campaign;
import com.phi.loyalty.cashback.domain.campaign.enums.CampaignStatusEnum;
import com.phi.loyalty.cashback.domain.campaign.enums.PaymentTypeEnum;
import com.phi.loyalty.cashback.domain.campaign.repository.CampaignRepository;
import com.phi.loyalty.cashback.domain.cashback.business.CashbackBO;
import com.phi.loyalty.cashback.domain.cashback.entity.Cashback;
import com.phi.loyalty.cashback.domain.cashback.service.CashbackService;
import com.phi.loyalty.cashback.domain.platform.business.PlatformBO;
import com.phi.loyalty.cashback.domain.platform.entity.Platform;
import com.phi.loyalty.cashback.domain.platform.service.PlatformService;
import com.phi.loyalty.cashback.domain.sponsor.business.SponsorBO;
import com.phi.loyalty.cashback.domain.sponsor.entity.Sponsor;
import com.phi.loyalty.cashback.domain.sponsor.service.SponsorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampaignServiceImplTest {

    @InjectMocks
    private CampaignServiceImpl campaignServiceImpl;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private CashbackService cashbackService;

    @Mock
    private PlatformService platformService;

    @Mock
    private SponsorService sponsorService;

    @Nested
    @DisplayName("Create Cashback Campaign")
    class Create {

        @DisplayName("Success - Successfully created new cashback campaign")
        @Test
        void should_createCashbackCampaign_when_success() {
            when(cashbackService.save(any(CashbackBO.class))).thenReturn(TestContextFactory.createCashback());
            when(platformService.findOrCreate(any(PlatformBO.class))).thenReturn(TestContextFactory.createPlatform());
            when(sponsorService.findOrCreate(any(SponsorBO.class), any(Platform.class))).thenReturn(TestContextFactory.createSponsor());
            when(campaignRepository.save(any(Campaign.class))).thenReturn(TestContextFactory.createCampaign());

            Campaign campaign = campaignServiceImpl.create(TestContextFactory.createCampaignBO(), TestContextFactory.createCashbackBO(), TestContextFactory.createPlatformBO(), TestContextFactory.createSponsorBO());

            assertEquals(1L, campaign.getId());
            assertEquals("Test Cashback", campaign.getName());
        }

    }

    @Nested
    @DisplayName("Retrieve Campaign")
    class Retrieve {

        @DisplayName("Success - Successfully retrieved the current active campaign for a giving platform")
        @Test
        void should_returnCampaign_when_campaignExistsGivenPlatformNameAndStatusAndEffectiveDateAndPaymentType() {
            when(campaignRepository.findByPlatformNameAndStatusAndEffectiveDateAndPaymentType(anyString(), any(), any(), any())).thenReturn(Optional.of(TestContextFactory.createCampaign()));

            Campaign campaign = campaignServiceImpl.findByPlatformNameAndPaymentType("FortPay", PaymentTypeEnum.PAYMENT_SLIP);

            assertEquals(1L, campaign.getId());
            assertEquals("Test Cashback", campaign.getName());
        }

        @DisplayName("Failure - Failed to find any active campaign for a giving platform")
        @Test
        void should_throwException_when_campaignsNotFoundForGivingPlatformNameAndStatusAndEffectiveDateAndPaymentType() {
            when(campaignRepository.findByPlatformNameAndStatusAndEffectiveDateAndPaymentType(anyString(), any(), any(), any())).thenReturn(Optional.empty());

            assertThrows(CampaignNotFoundException.class, () -> campaignServiceImpl.findByPlatformNameAndPaymentType("FortPay", PaymentTypeEnum.PAYMENT_SLIP));
        }

        @DisplayName("Success - Successfully found an existing campaign")
        @Test
        void should_returnCampaign_when_campaignExistsForGivingCampaignId() {
            when(campaignRepository.findById(anyLong())).thenReturn(Optional.of(TestContextFactory.createCampaign()));

            Campaign result = campaignServiceImpl.retrieve(1L);

            assertEquals(1L, result.getId());
            assertEquals("Test Cashback", result.getName());
        }

        @DisplayName("Failure - Failed to retrieve a campaign")
        @Test
        void should_throwException_when_campaignNotFoundForGivingCampaignId() {
            when(campaignRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThrows(CampaignNotFoundException.class, () -> campaignServiceImpl.retrieve(1L));
        }

        @DisplayName("Success - Successfully retrieved all campaigns of a giving platform")
        @Test
        void should_returnCampaignList_when_campaignsExistGivingPlatformNameAndStatus() {
            PageRequest pageRequest = PageRequest.of(0, 20);
            when(campaignRepository.findByPlatformNameAndStatus(anyString(), any(), any())).thenReturn(new PageImpl<>(List.of(TestContextFactory.createCampaign())));

            Page<Campaign> result = campaignServiceImpl.retrieve("FortPay", null, pageRequest);

            assertFalse(result.isEmpty());
        }

        @DisplayName("Success - Successfully retrieved the current active campaign for a giving campaign id")
        @Test
        void should_returnCampaign_when_campaignExistsGivingIdAndStatusAndEffectiveDate() {
            when(campaignRepository.findByIdAndStatusAndEffectiveDate(anyLong(), any(), any())).thenReturn(Optional.of(TestContextFactory.createCampaign()));

            Campaign campaign = campaignServiceImpl.retrieve(1L, CampaignStatusEnum.CREATED);

            assertEquals(1L, campaign.getId());
            assertEquals("Test Cashback", campaign.getName());
        }

        @DisplayName("Failure - Failed to find any active campaign for a giving campaign id")
        @Test
        void should_throwException_when_campaignNotFoundForGivingCampaignIdAndStatusAndEffectiveDate() {
            when(campaignRepository.findByIdAndStatusAndEffectiveDate(anyLong(), any(), any())).thenReturn(Optional.empty());

            assertThrows(CampaignNotFoundException.class, () -> campaignServiceImpl.retrieve(1L, CampaignStatusEnum.CREATED));
        }

    }

    @Nested
    @DisplayName("Suspend Active Campaign")
    class Suspend {

        @DisplayName("Success - Successfully suspended a giving active campaign")
        @Test
        void should_suspendCampaign_when_campaignExists() {
            Campaign campaign = TestContextFactory.createCampaign();
            when(campaignRepository.findByIdAndStatusAndEndsAtGreaterThanEqual(anyLong(), any(), any())).thenReturn(Optional.of(campaign));
            when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);

            campaignServiceImpl.suspend(1L);

            verify(campaignRepository).save(campaign);
            assertEquals(CampaignStatusEnum.SUSPENDED, campaign.getStatus());
        }

        @DisplayName("Failure - Failed to find an active or scheduled campaign to suspend")
        @Test
        void should_throwException_when_campaignNotFound() {
            when(campaignRepository.findByIdAndStatusAndEndsAtGreaterThanEqual(anyLong(), any(), any())).thenReturn(Optional.empty());

            assertThrows(CampaignNotFoundException.class, () -> campaignServiceImpl.suspend(1L));
        }

    }

    @Nested
    @DisplayName("Reschedule a Campaign")
    class Reschedule {

        @DisplayName("Success - Successfully rescheduled a giving created campaign")
        @Test
        void should_rescheduleCampaign_when_validDate() {
            Campaign campaign = TestContextFactory.createCampaign();
            when(campaignRepository.findByIdAndStatusAndEffectiveDate(anyLong(), any(), any())).thenReturn(Optional.of(campaign));
            when(campaignRepository.save(any(Campaign.class))).thenReturn(campaign);

            campaignServiceImpl.reschedule(1L, TestContextFactory.createCampaignBO());

            verify(campaignRepository).findByIdAndStatusAndEffectiveDate(anyLong(), any(), any());
            verify(campaignRepository).save(any(Campaign.class));
        }

        @DisplayName("Failure - Failed to find an active or scheduled campaign to suspend")
        @Test
        void should_throwException_when_campaignNotFound() {
            when(campaignRepository.findByIdAndStatusAndEffectiveDate(anyLong(), any(), any())).thenReturn(Optional.empty());

            assertThrows(CampaignNotFoundException.class, () -> campaignServiceImpl.reschedule(1L, TestContextFactory.createCampaignBO()));
        }

    }

    @Nested
    @DisplayName("Modify a Campaign")
    class Modify {

        @DisplayName("Success - Successfully modify campaign")
        @Test
        void should_modifyCampaign_when_campaignExists() {
            Optional<Campaign> optionalCampaign = Optional.of(TestContextFactory.createCampaign());
            when(campaignRepository.findByIdAndStatusAndStartsAtGreaterThan(anyLong(), any(CampaignStatusEnum.class), any(LocalDate.class))).thenReturn(optionalCampaign);

            campaignServiceImpl.modify(1L, TestContextFactory.createCampaignBO(), TestContextFactory.createCashbackBO());

            verify(campaignRepository).findByIdAndStatusAndStartsAtGreaterThan(anyLong(), any(CampaignStatusEnum.class), any(LocalDate.class));
        }

        @DisplayName("Failed - Failed to find any scheduled campaign found for this id")
        @Test
        void should_throwException_when_campaignNotFound() {
            when(campaignRepository.findByIdAndStatusAndStartsAtGreaterThan(anyLong(), any(CampaignStatusEnum.class), any(LocalDate.class))).thenReturn(Optional.empty());

            assertThrows(CampaignNotFoundException.class, () -> campaignServiceImpl.modify(1L, TestContextFactory.createCampaignBO(), TestContextFactory.createCashbackBO()));
        }

        @DisplayName("Failed - Failed to validate effective date on modify because the fields startAt and endsAt are the same")
        @Test
        void should_throwException_when_effectiveDateValidationFailed() {
            CampaignBO failedCampaignBO = CampaignBO.builder()
                    .startsAt(LocalDate.parse("1020-12-29"))
                    .endsAt(LocalDate.parse("1020-12-29"))
                    .build();

            assertThrows(InvalidEffectiveDateException.class, () -> campaignServiceImpl.modify(1L, failedCampaignBO, TestContextFactory.createCashbackBO()));
        }

    }

    static class TestContextFactory {

        private static final Faker FAKER = new Faker(new Locale("pt-BR"));

        public static PlatformBO createPlatformBO() {
            return PlatformBO.builder()
                    .balanceType("FortPay")
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
                    .document(FAKER.numerify("???????????"))
                    .platform(createPlatform())
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        public static SponsorBO createSponsorBO() {
            return SponsorBO.builder()
                    .id(332040L)
                    .document(FAKER.numerify("???????????"))
                    .build();
        }

        public static CashbackBO createCashbackBO() {
            return CashbackBO.builder()
                    .maximumPaymentValue(500L)
                    .percentage(new BigDecimal("0.1"))
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

        public static CampaignBO createCampaignBO() {
            return CampaignBO.builder()
                    .name("Test Cashback")
                    .startsAt(LocalDate.now())
                    .endsAt(LocalDate.now().plusMonths(1))
                    .paymentType(PaymentTypeEnum.PAYMENT_SLIP)
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
                    .paymentType(PaymentTypeEnum.PAYMENT_SLIP)
                    .build();
        }

    }

}
*/