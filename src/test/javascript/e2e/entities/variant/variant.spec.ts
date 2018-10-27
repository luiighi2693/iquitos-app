/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VariantComponentsPage, VariantDeleteDialog, VariantUpdatePage } from './variant.page-object';

const expect = chai.expect;

describe('Variant e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let variantUpdatePage: VariantUpdatePage;
    let variantComponentsPage: VariantComponentsPage;
    let variantDeleteDialog: VariantDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Variants', async () => {
        await navBarPage.goToEntity('variant');
        variantComponentsPage = new VariantComponentsPage();
        expect(await variantComponentsPage.getTitle()).to.eq('Variants');
    });

    it('should load create Variant page', async () => {
        await variantComponentsPage.clickOnCreateButton();
        variantUpdatePage = new VariantUpdatePage();
        expect(await variantUpdatePage.getPageTitle()).to.eq('Create or edit a Variant');
        await variantUpdatePage.cancel();
    });

    it('should create and save Variants', async () => {
        const nbButtonsBeforeCreate = await variantComponentsPage.countDeleteButtons();

        await variantComponentsPage.clickOnCreateButton();
        await promise.all([
            variantUpdatePage.setNameInput('name'),
            variantUpdatePage.setDescriptionInput('description'),
            variantUpdatePage.setPriceSellInput('5'),
            variantUpdatePage.setPricePurchaseInput('5')
            // variantUpdatePage.productsSelectLastOption(),
        ]);
        expect(await variantUpdatePage.getNameInput()).to.eq('name');
        expect(await variantUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await variantUpdatePage.getPriceSellInput()).to.eq('5');
        expect(await variantUpdatePage.getPricePurchaseInput()).to.eq('5');
        await variantUpdatePage.save();
        expect(await variantUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await variantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Variant', async () => {
        const nbButtonsBeforeDelete = await variantComponentsPage.countDeleteButtons();
        await variantComponentsPage.clickOnLastDeleteButton();

        variantDeleteDialog = new VariantDeleteDialog();
        expect(await variantDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Variant?');
        await variantDeleteDialog.clickOnConfirmButton();

        expect(await variantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
