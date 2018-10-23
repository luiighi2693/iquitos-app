/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AmortizationComponentsPage, AmortizationDeleteDialog, AmortizationUpdatePage } from './amortization.page-object';

const expect = chai.expect;

describe('Amortization e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let amortizationUpdatePage: AmortizationUpdatePage;
    let amortizationComponentsPage: AmortizationComponentsPage;
    let amortizationDeleteDialog: AmortizationDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Amortizations', async () => {
        await navBarPage.goToEntity('amortization');
        amortizationComponentsPage = new AmortizationComponentsPage();
        expect(await amortizationComponentsPage.getTitle()).to.eq('Amortizations');
    });

    it('should load create Amortization page', async () => {
        await amortizationComponentsPage.clickOnCreateButton();
        amortizationUpdatePage = new AmortizationUpdatePage();
        expect(await amortizationUpdatePage.getPageTitle()).to.eq('Create or edit a Amortization');
        await amortizationUpdatePage.cancel();
    });

    it('should create and save Amortizations', async () => {
        const nbButtonsBeforeCreate = await amortizationComponentsPage.countDeleteButtons();

        await amortizationComponentsPage.clickOnCreateButton();
        await promise.all([
            amortizationUpdatePage.setAmountToPayInput('5'),
            amortizationUpdatePage.setAmountPayedInput('5'),
            amortizationUpdatePage.setEmissionDateInput('2000-12-31'),
            amortizationUpdatePage.setDocumentCodeInput('documentCode'),
            amortizationUpdatePage.setGlossInput('gloss'),
            amortizationUpdatePage.documentTypeSellSelectLastOption(),
            amortizationUpdatePage.paymentTypeSelectLastOption(),
            amortizationUpdatePage.sellSelectLastOption()
        ]);
        expect(await amortizationUpdatePage.getAmountToPayInput()).to.eq('5');
        expect(await amortizationUpdatePage.getAmountPayedInput()).to.eq('5');
        expect(await amortizationUpdatePage.getEmissionDateInput()).to.eq('2000-12-31');
        expect(await amortizationUpdatePage.getDocumentCodeInput()).to.eq('documentCode');
        expect(await amortizationUpdatePage.getGlossInput()).to.eq('gloss');
        await amortizationUpdatePage.save();
        expect(await amortizationUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await amortizationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Amortization', async () => {
        const nbButtonsBeforeDelete = await amortizationComponentsPage.countDeleteButtons();
        await amortizationComponentsPage.clickOnLastDeleteButton();

        amortizationDeleteDialog = new AmortizationDeleteDialog();
        expect(await amortizationDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Amortization?');
        await amortizationDeleteDialog.clickOnConfirmButton();

        expect(await amortizationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
