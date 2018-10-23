/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DocumentTypeComponentsPage, DocumentTypeDeleteDialog, DocumentTypeUpdatePage } from './document-type.page-object';

const expect = chai.expect;

describe('DocumentType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let documentTypeUpdatePage: DocumentTypeUpdatePage;
    let documentTypeComponentsPage: DocumentTypeComponentsPage;
    let documentTypeDeleteDialog: DocumentTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load DocumentTypes', async () => {
        await navBarPage.goToEntity('document-type');
        documentTypeComponentsPage = new DocumentTypeComponentsPage();
        expect(await documentTypeComponentsPage.getTitle()).to.eq('Document Types');
    });

    it('should load create DocumentType page', async () => {
        await documentTypeComponentsPage.clickOnCreateButton();
        documentTypeUpdatePage = new DocumentTypeUpdatePage();
        expect(await documentTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Document Type');
        await documentTypeUpdatePage.cancel();
    });

    it('should create and save DocumentTypes', async () => {
        const nbButtonsBeforeCreate = await documentTypeComponentsPage.countDeleteButtons();

        await documentTypeComponentsPage.clickOnCreateButton();
        await promise.all([documentTypeUpdatePage.setValueInput('value'), documentTypeUpdatePage.setMetaDataInput('metaData')]);
        expect(await documentTypeUpdatePage.getValueInput()).to.eq('value');
        expect(await documentTypeUpdatePage.getMetaDataInput()).to.eq('metaData');
        await documentTypeUpdatePage.save();
        expect(await documentTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await documentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last DocumentType', async () => {
        const nbButtonsBeforeDelete = await documentTypeComponentsPage.countDeleteButtons();
        await documentTypeComponentsPage.clickOnLastDeleteButton();

        documentTypeDeleteDialog = new DocumentTypeDeleteDialog();
        expect(await documentTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Document Type?');
        await documentTypeDeleteDialog.clickOnConfirmButton();

        expect(await documentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
