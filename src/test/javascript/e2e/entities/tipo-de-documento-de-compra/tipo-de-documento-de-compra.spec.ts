/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    TipoDeDocumentoDeCompraComponentsPage,
    TipoDeDocumentoDeCompraDeleteDialog,
    TipoDeDocumentoDeCompraUpdatePage
} from './tipo-de-documento-de-compra.page-object';

const expect = chai.expect;

describe('TipoDeDocumentoDeCompra e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tipoDeDocumentoDeCompraUpdatePage: TipoDeDocumentoDeCompraUpdatePage;
    let tipoDeDocumentoDeCompraComponentsPage: TipoDeDocumentoDeCompraComponentsPage;
    let tipoDeDocumentoDeCompraDeleteDialog: TipoDeDocumentoDeCompraDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TipoDeDocumentoDeCompras', async () => {
        await navBarPage.goToEntity('tipo-de-documento-de-compra');
        tipoDeDocumentoDeCompraComponentsPage = new TipoDeDocumentoDeCompraComponentsPage();
        expect(await tipoDeDocumentoDeCompraComponentsPage.getTitle()).to.eq('Tipo De Documento De Compras');
    });

    it('should load create TipoDeDocumentoDeCompra page', async () => {
        await tipoDeDocumentoDeCompraComponentsPage.clickOnCreateButton();
        tipoDeDocumentoDeCompraUpdatePage = new TipoDeDocumentoDeCompraUpdatePage();
        expect(await tipoDeDocumentoDeCompraUpdatePage.getPageTitle()).to.eq('Create or edit a Tipo De Documento De Compra');
        await tipoDeDocumentoDeCompraUpdatePage.cancel();
    });

    it('should create and save TipoDeDocumentoDeCompras', async () => {
        const nbButtonsBeforeCreate = await tipoDeDocumentoDeCompraComponentsPage.countDeleteButtons();

        await tipoDeDocumentoDeCompraComponentsPage.clickOnCreateButton();
        await promise.all([tipoDeDocumentoDeCompraUpdatePage.setNombreInput('nombre')]);
        expect(await tipoDeDocumentoDeCompraUpdatePage.getNombreInput()).to.eq('nombre');
        await tipoDeDocumentoDeCompraUpdatePage.save();
        expect(await tipoDeDocumentoDeCompraUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tipoDeDocumentoDeCompraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TipoDeDocumentoDeCompra', async () => {
        const nbButtonsBeforeDelete = await tipoDeDocumentoDeCompraComponentsPage.countDeleteButtons();
        await tipoDeDocumentoDeCompraComponentsPage.clickOnLastDeleteButton();

        tipoDeDocumentoDeCompraDeleteDialog = new TipoDeDocumentoDeCompraDeleteDialog();
        expect(await tipoDeDocumentoDeCompraDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Tipo De Documento De Compra?'
        );
        await tipoDeDocumentoDeCompraDeleteDialog.clickOnConfirmButton();

        expect(await tipoDeDocumentoDeCompraComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
