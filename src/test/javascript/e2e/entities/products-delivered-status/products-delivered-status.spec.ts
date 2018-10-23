/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ProductsDeliveredStatusComponentsPage,
    ProductsDeliveredStatusDeleteDialog,
    ProductsDeliveredStatusUpdatePage
} from './products-delivered-status.page-object';

const expect = chai.expect;

describe('ProductsDeliveredStatus e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productsDeliveredStatusUpdatePage: ProductsDeliveredStatusUpdatePage;
    let productsDeliveredStatusComponentsPage: ProductsDeliveredStatusComponentsPage;
    let productsDeliveredStatusDeleteDialog: ProductsDeliveredStatusDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductsDeliveredStatuses', async () => {
        await navBarPage.goToEntity('products-delivered-status');
        productsDeliveredStatusComponentsPage = new ProductsDeliveredStatusComponentsPage();
        expect(await productsDeliveredStatusComponentsPage.getTitle()).to.eq('Products Delivered Statuses');
    });

    it('should load create ProductsDeliveredStatus page', async () => {
        await productsDeliveredStatusComponentsPage.clickOnCreateButton();
        productsDeliveredStatusUpdatePage = new ProductsDeliveredStatusUpdatePage();
        expect(await productsDeliveredStatusUpdatePage.getPageTitle()).to.eq('Create or edit a Products Delivered Status');
        await productsDeliveredStatusUpdatePage.cancel();
    });

    it('should create and save ProductsDeliveredStatuses', async () => {
        const nbButtonsBeforeCreate = await productsDeliveredStatusComponentsPage.countDeleteButtons();

        await productsDeliveredStatusComponentsPage.clickOnCreateButton();
        await promise.all([
            productsDeliveredStatusUpdatePage.setValueInput('value'),
            productsDeliveredStatusUpdatePage.setMetaDataInput('metaData')
        ]);
        expect(await productsDeliveredStatusUpdatePage.getValueInput()).to.eq('value');
        expect(await productsDeliveredStatusUpdatePage.getMetaDataInput()).to.eq('metaData');
        await productsDeliveredStatusUpdatePage.save();
        expect(await productsDeliveredStatusUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productsDeliveredStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductsDeliveredStatus', async () => {
        const nbButtonsBeforeDelete = await productsDeliveredStatusComponentsPage.countDeleteButtons();
        await productsDeliveredStatusComponentsPage.clickOnLastDeleteButton();

        productsDeliveredStatusDeleteDialog = new ProductsDeliveredStatusDeleteDialog();
        expect(await productsDeliveredStatusDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Products Delivered Status?'
        );
        await productsDeliveredStatusDeleteDialog.clickOnConfirmButton();

        expect(await productsDeliveredStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
