/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DocumentTypeSellComponentsPage, DocumentTypeSellDeleteDialog, DocumentTypeSellUpdatePage } from './document-type-sell.page-object';

const expect = chai.expect;

describe('DocumentTypeSell e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let documentTypeSellUpdatePage: DocumentTypeSellUpdatePage;
    let documentTypeSellComponentsPage: DocumentTypeSellComponentsPage;
    let documentTypeSellDeleteDialog: DocumentTypeSellDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load DocumentTypeSells', async () => {
        await navBarPage.goToEntity('document-type-sell');
        documentTypeSellComponentsPage = new DocumentTypeSellComponentsPage();
        expect(await documentTypeSellComponentsPage.getTitle()).to.eq('Document Type Sells');
    });

    it('should load create DocumentTypeSell page', async () => {
        await documentTypeSellComponentsPage.clickOnCreateButton();
        documentTypeSellUpdatePage = new DocumentTypeSellUpdatePage();
        expect(await documentTypeSellUpdatePage.getPageTitle()).to.eq('Create or edit a Document Type Sell');
        await documentTypeSellUpdatePage.cancel();
    });

    it('should create and save DocumentTypeSells', async () => {
        const nbButtonsBeforeCreate = await documentTypeSellComponentsPage.countDeleteButtons();

        await documentTypeSellComponentsPage.clickOnCreateButton();
        await promise.all([documentTypeSellUpdatePage.setValueInput('value'), documentTypeSellUpdatePage.setMetaDataInput('metaData')]);
        expect(await documentTypeSellUpdatePage.getValueInput()).to.eq('value');
        expect(await documentTypeSellUpdatePage.getMetaDataInput()).to.eq('metaData');
        await documentTypeSellUpdatePage.save();
        expect(await documentTypeSellUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await documentTypeSellComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last DocumentTypeSell', async () => {
        const nbButtonsBeforeDelete = await documentTypeSellComponentsPage.countDeleteButtons();
        await documentTypeSellComponentsPage.clickOnLastDeleteButton();

        documentTypeSellDeleteDialog = new DocumentTypeSellDeleteDialog();
        expect(await documentTypeSellDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Document Type Sell?');
        await documentTypeSellDeleteDialog.clickOnConfirmButton();

        expect(await documentTypeSellComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
