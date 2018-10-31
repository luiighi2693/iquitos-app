/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    TipoDeDocumentoDeVentaComponentsPage,
    TipoDeDocumentoDeVentaDeleteDialog,
    TipoDeDocumentoDeVentaUpdatePage
} from './tipo-de-documento-de-venta.page-object';

const expect = chai.expect;

describe('TipoDeDocumentoDeVenta e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let tipoDeDocumentoDeVentaUpdatePage: TipoDeDocumentoDeVentaUpdatePage;
    let tipoDeDocumentoDeVentaComponentsPage: TipoDeDocumentoDeVentaComponentsPage;
    let tipoDeDocumentoDeVentaDeleteDialog: TipoDeDocumentoDeVentaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load TipoDeDocumentoDeVentas', async () => {
        await navBarPage.goToEntity('tipo-de-documento-de-venta');
        tipoDeDocumentoDeVentaComponentsPage = new TipoDeDocumentoDeVentaComponentsPage();
        expect(await tipoDeDocumentoDeVentaComponentsPage.getTitle()).to.eq('Tipo De Documento De Ventas');
    });

    it('should load create TipoDeDocumentoDeVenta page', async () => {
        await tipoDeDocumentoDeVentaComponentsPage.clickOnCreateButton();
        tipoDeDocumentoDeVentaUpdatePage = new TipoDeDocumentoDeVentaUpdatePage();
        expect(await tipoDeDocumentoDeVentaUpdatePage.getPageTitle()).to.eq('Create or edit a Tipo De Documento De Venta');
        await tipoDeDocumentoDeVentaUpdatePage.cancel();
    });

    it('should create and save TipoDeDocumentoDeVentas', async () => {
        const nbButtonsBeforeCreate = await tipoDeDocumentoDeVentaComponentsPage.countDeleteButtons();

        await tipoDeDocumentoDeVentaComponentsPage.clickOnCreateButton();
        await promise.all([tipoDeDocumentoDeVentaUpdatePage.setNombreInput('nombre')]);
        expect(await tipoDeDocumentoDeVentaUpdatePage.getNombreInput()).to.eq('nombre');
        await tipoDeDocumentoDeVentaUpdatePage.save();
        expect(await tipoDeDocumentoDeVentaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await tipoDeDocumentoDeVentaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last TipoDeDocumentoDeVenta', async () => {
        const nbButtonsBeforeDelete = await tipoDeDocumentoDeVentaComponentsPage.countDeleteButtons();
        await tipoDeDocumentoDeVentaComponentsPage.clickOnLastDeleteButton();

        tipoDeDocumentoDeVentaDeleteDialog = new TipoDeDocumentoDeVentaDeleteDialog();
        expect(await tipoDeDocumentoDeVentaDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Tipo De Documento De Venta?'
        );
        await tipoDeDocumentoDeVentaDeleteDialog.clickOnConfirmButton();

        expect(await tipoDeDocumentoDeVentaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
