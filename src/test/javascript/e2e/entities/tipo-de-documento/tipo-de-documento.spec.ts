/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TipoDeDocumentoComponentsPage, TipoDeDocumentoDeleteDialog, TipoDeDocumentoUpdatePage } from './tipo-de-documento.page-object';

const expect = chai.expect;

describe('TipoDeDocumento e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tipoDeDocumentoUpdatePage: TipoDeDocumentoUpdatePage;
    let tipoDeDocumentoComponentsPage: TipoDeDocumentoComponentsPage;
    let tipoDeDocumentoDeleteDialog: TipoDeDocumentoDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TipoDeDocumentos', async () => {
        await navBarPage.goToEntity('tipo-de-documento');
        tipoDeDocumentoComponentsPage = new TipoDeDocumentoComponentsPage();
        expect(await tipoDeDocumentoComponentsPage.getTitle()).to.eq('Tipo De Documentos');
    });

    it('should load create TipoDeDocumento page', async () => {
        await tipoDeDocumentoComponentsPage.clickOnCreateButton();
        tipoDeDocumentoUpdatePage = new TipoDeDocumentoUpdatePage();
        expect(await tipoDeDocumentoUpdatePage.getPageTitle()).to.eq('Create or edit a Tipo De Documento');
        await tipoDeDocumentoUpdatePage.cancel();
    });

    it('should create and save TipoDeDocumentos', async () => {
        const nbButtonsBeforeCreate = await tipoDeDocumentoComponentsPage.countDeleteButtons();

        await tipoDeDocumentoComponentsPage.clickOnCreateButton();
        await promise.all([tipoDeDocumentoUpdatePage.setNombreInput('nombre')]);
        expect(await tipoDeDocumentoUpdatePage.getNombreInput()).to.eq('nombre');
        await tipoDeDocumentoUpdatePage.save();
        expect(await tipoDeDocumentoUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tipoDeDocumentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TipoDeDocumento', async () => {
        const nbButtonsBeforeDelete = await tipoDeDocumentoComponentsPage.countDeleteButtons();
        await tipoDeDocumentoComponentsPage.clickOnLastDeleteButton();

        tipoDeDocumentoDeleteDialog = new TipoDeDocumentoDeleteDialog();
        expect(await tipoDeDocumentoDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Tipo De Documento?');
        await tipoDeDocumentoDeleteDialog.clickOnConfirmButton();

        expect(await tipoDeDocumentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
