/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductoDetalleComponentsPage, ProductoDetalleDeleteDialog, ProductoDetalleUpdatePage } from './producto-detalle.page-object';

const expect = chai.expect;

describe('ProductoDetalle e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productoDetalleUpdatePage: ProductoDetalleUpdatePage;
    let productoDetalleComponentsPage: ProductoDetalleComponentsPage;
    let productoDetalleDeleteDialog: ProductoDetalleDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductoDetalles', async () => {
        await navBarPage.goToEntity('producto-detalle');
        productoDetalleComponentsPage = new ProductoDetalleComponentsPage();
        expect(await productoDetalleComponentsPage.getTitle()).to.eq('Producto Detalles');
    });

    it('should load create ProductoDetalle page', async () => {
        await productoDetalleComponentsPage.clickOnCreateButton();
        productoDetalleUpdatePage = new ProductoDetalleUpdatePage();
        expect(await productoDetalleUpdatePage.getPageTitle()).to.eq('Create or edit a Producto Detalle');
        await productoDetalleUpdatePage.cancel();
    });

    it('should create and save ProductoDetalles', async () => {
        const nbButtonsBeforeCreate = await productoDetalleComponentsPage.countDeleteButtons();

        await productoDetalleComponentsPage.clickOnCreateButton();
        await promise.all([
            productoDetalleUpdatePage.setCantidadInput('5'),
            productoDetalleUpdatePage.setProductoLabelInput('productoLabel'),
            productoDetalleUpdatePage.setPrecioVentaInput('5')
            // productoDetalleUpdatePage.variantesSelectLastOption(),
            // productoDetalleUpdatePage.productosSelectLastOption(),
        ]);
        expect(await productoDetalleUpdatePage.getCantidadInput()).to.eq('5');
        expect(await productoDetalleUpdatePage.getProductoLabelInput()).to.eq('productoLabel');
        expect(await productoDetalleUpdatePage.getPrecioVentaInput()).to.eq('5');
        await productoDetalleUpdatePage.save();
        expect(await productoDetalleUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productoDetalleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductoDetalle', async () => {
        const nbButtonsBeforeDelete = await productoDetalleComponentsPage.countDeleteButtons();
        await productoDetalleComponentsPage.clickOnLastDeleteButton();

        productoDetalleDeleteDialog = new ProductoDetalleDeleteDialog();
        expect(await productoDetalleDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Producto Detalle?');
        await productoDetalleDeleteDialog.clickOnConfirmButton();

        expect(await productoDetalleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
