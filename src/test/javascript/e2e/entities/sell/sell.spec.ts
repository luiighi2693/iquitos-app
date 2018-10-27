/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SellComponentsPage, SellDeleteDialog, SellUpdatePage } from './sell.page-object';

const expect = chai.expect;

describe('Sell e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let sellUpdatePage: SellUpdatePage;
    let sellComponentsPage: SellComponentsPage;
    let sellDeleteDialog: SellDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Sells', async () => {
        await navBarPage.goToEntity('sell');
        sellComponentsPage = new SellComponentsPage();
        expect(await sellComponentsPage.getTitle()).to.eq('Sells');
    });

    it('should load create Sell page', async () => {
        await sellComponentsPage.clickOnCreateButton();
        sellUpdatePage = new SellUpdatePage();
        expect(await sellUpdatePage.getPageTitle()).to.eq('Create or edit a Sell');
        await sellUpdatePage.cancel();
    });

    it('should create and save Sells', async () => {
        const nbButtonsBeforeCreate = await sellComponentsPage.countDeleteButtons();

        await sellComponentsPage.clickOnCreateButton();
        await promise.all([
            sellUpdatePage.setCodeInput('code'),
            sellUpdatePage.setSubTotalAmountInput('5'),
            sellUpdatePage.setTaxAmountInput('5'),
            sellUpdatePage.setTotalAmountInput('5'),
            sellUpdatePage.setDateInput('2000-12-31'),
            sellUpdatePage.statusSelectLastOption(),
            sellUpdatePage.setGlossInput('gloss'),
            sellUpdatePage.setMetaDataInput('metaData'),
            sellUpdatePage.clientSelectLastOption(),
            sellUpdatePage.employeeSelectLastOption(),
            sellUpdatePage.boxSelectLastOption(),
            sellUpdatePage.documentTypeSellSelectLastOption(),
            sellUpdatePage.paymentTypeSelectLastOption(),
            sellUpdatePage.productsDeliveredStatusSelectLastOption()
            // sellUpdatePage.productsSelectLastOption(),
        ]);
        expect(await sellUpdatePage.getCodeInput()).to.eq('code');
        expect(await sellUpdatePage.getSubTotalAmountInput()).to.eq('5');
        expect(await sellUpdatePage.getTaxAmountInput()).to.eq('5');
        expect(await sellUpdatePage.getTotalAmountInput()).to.eq('5');
        expect(await sellUpdatePage.getDateInput()).to.eq('2000-12-31');
        expect(await sellUpdatePage.getGlossInput()).to.eq('gloss');
        expect(await sellUpdatePage.getMetaDataInput()).to.eq('metaData');
        await sellUpdatePage.save();
        expect(await sellUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await sellComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Sell', async () => {
        const nbButtonsBeforeDelete = await sellComponentsPage.countDeleteButtons();
        await sellComponentsPage.clickOnLastDeleteButton();

        sellDeleteDialog = new SellDeleteDialog();
        expect(await sellDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Sell?');
        await sellDeleteDialog.clickOnConfirmButton();

        expect(await sellComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
