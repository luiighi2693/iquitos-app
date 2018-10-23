/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PurchaseComponentsPage, PurchaseDeleteDialog, PurchaseUpdatePage } from './purchase.page-object';

const expect = chai.expect;

describe('Purchase e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let purchaseUpdatePage: PurchaseUpdatePage;
    let purchaseComponentsPage: PurchaseComponentsPage;
    let purchaseDeleteDialog: PurchaseDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Purchases', async () => {
        await navBarPage.goToEntity('purchase');
        purchaseComponentsPage = new PurchaseComponentsPage();
        expect(await purchaseComponentsPage.getTitle()).to.eq('Purchases');
    });

    it('should load create Purchase page', async () => {
        await purchaseComponentsPage.clickOnCreateButton();
        purchaseUpdatePage = new PurchaseUpdatePage();
        expect(await purchaseUpdatePage.getPageTitle()).to.eq('Create or edit a Purchase');
        await purchaseUpdatePage.cancel();
    });

    it('should create and save Purchases', async () => {
        const nbButtonsBeforeCreate = await purchaseComponentsPage.countDeleteButtons();

        await purchaseComponentsPage.clickOnCreateButton();
        await promise.all([
            purchaseUpdatePage.setDateInput('2000-12-31'),
            purchaseUpdatePage.setRemisionGuideInput('remisionGuide'),
            purchaseUpdatePage.setDocumentNumberInput('documentNumber'),
            purchaseUpdatePage.locationSelectLastOption(),
            purchaseUpdatePage.setTotalAmountInput('5'),
            purchaseUpdatePage.setCorrelativeInput('correlative'),
            purchaseUpdatePage.paymentPurchaseTypeSelectLastOption(),
            purchaseUpdatePage.providerSelectLastOption(),
            purchaseUpdatePage.documentTypePurchaseSelectLastOption(),
            purchaseUpdatePage.purchaseStatusSelectLastOption(),
            purchaseUpdatePage.boxSelectLastOption()
        ]);
        expect(await purchaseUpdatePage.getDateInput()).to.eq('2000-12-31');
        expect(await purchaseUpdatePage.getRemisionGuideInput()).to.eq('remisionGuide');
        expect(await purchaseUpdatePage.getDocumentNumberInput()).to.eq('documentNumber');
        expect(await purchaseUpdatePage.getTotalAmountInput()).to.eq('5');
        expect(await purchaseUpdatePage.getCorrelativeInput()).to.eq('correlative');
        await purchaseUpdatePage.save();
        expect(await purchaseUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await purchaseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Purchase', async () => {
        const nbButtonsBeforeDelete = await purchaseComponentsPage.countDeleteButtons();
        await purchaseComponentsPage.clickOnLastDeleteButton();

        purchaseDeleteDialog = new PurchaseDeleteDialog();
        expect(await purchaseDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Purchase?');
        await purchaseDeleteDialog.clickOnConfirmButton();

        expect(await purchaseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
