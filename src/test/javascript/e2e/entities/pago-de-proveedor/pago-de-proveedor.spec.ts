/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PagoDeProveedorComponentsPage, PagoDeProveedorDeleteDialog, PagoDeProveedorUpdatePage } from './pago-de-proveedor.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('PagoDeProveedor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let pagoDeProveedorUpdatePage: PagoDeProveedorUpdatePage;
    let pagoDeProveedorComponentsPage: PagoDeProveedorComponentsPage;
    let pagoDeProveedorDeleteDialog: PagoDeProveedorDeleteDialog;
    const fileNameToUpload = 'logo-jhipster.png';
    const fileToUpload = '../../../../../main/webapp/content/images/' + fileNameToUpload;
    const absolutePath = path.resolve(__dirname, fileToUpload);

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PagoDeProveedors', async () => {
        await navBarPage.goToEntity('pago-de-proveedor');
        pagoDeProveedorComponentsPage = new PagoDeProveedorComponentsPage();
        expect(await pagoDeProveedorComponentsPage.getTitle()).to.eq('Pago De Proveedors');
    });

    it('should load create PagoDeProveedor page', async () => {
        await pagoDeProveedorComponentsPage.clickOnCreateButton();
        pagoDeProveedorUpdatePage = new PagoDeProveedorUpdatePage();
        expect(await pagoDeProveedorUpdatePage.getPageTitle()).to.eq('Create or edit a Pago De Proveedor');
        await pagoDeProveedorUpdatePage.cancel();
    });

    it('should create and save PagoDeProveedors', async () => {
        const nbButtonsBeforeCreate = await pagoDeProveedorComponentsPage.countDeleteButtons();

        await pagoDeProveedorComponentsPage.clickOnCreateButton();
        await promise.all([
            pagoDeProveedorUpdatePage.setMontoInput('5'),
            pagoDeProveedorUpdatePage.setMontoPagadoInput('5'),
            pagoDeProveedorUpdatePage.setFechaInput('2000-12-31'),
            pagoDeProveedorUpdatePage.setCodigoDeDocumentoInput('codigoDeDocumento'),
            pagoDeProveedorUpdatePage.setGlosaInput('glosa'),
            pagoDeProveedorUpdatePage.setImagenInput(absolutePath),
            pagoDeProveedorUpdatePage.tipoDeDocumentoDeCompraSelectLastOption(),
            pagoDeProveedorUpdatePage.tipoDePagoSelectLastOption()
        ]);
        expect(await pagoDeProveedorUpdatePage.getMontoInput()).to.eq('5');
        expect(await pagoDeProveedorUpdatePage.getMontoPagadoInput()).to.eq('5');
        expect(await pagoDeProveedorUpdatePage.getFechaInput()).to.eq('2000-12-31');
        expect(await pagoDeProveedorUpdatePage.getCodigoDeDocumentoInput()).to.eq('codigoDeDocumento');
        expect(await pagoDeProveedorUpdatePage.getGlosaInput()).to.eq('glosa');
        expect(await pagoDeProveedorUpdatePage.getImagenInput()).to.endsWith(fileNameToUpload);
        await pagoDeProveedorUpdatePage.save();
        expect(await pagoDeProveedorUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await pagoDeProveedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PagoDeProveedor', async () => {
        const nbButtonsBeforeDelete = await pagoDeProveedorComponentsPage.countDeleteButtons();
        await pagoDeProveedorComponentsPage.clickOnLastDeleteButton();

        pagoDeProveedorDeleteDialog = new PagoDeProveedorDeleteDialog();
        expect(await pagoDeProveedorDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Pago De Proveedor?');
        await pagoDeProveedorDeleteDialog.clickOnConfirmButton();

        expect(await pagoDeProveedorComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
